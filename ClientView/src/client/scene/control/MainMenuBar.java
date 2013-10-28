package client.scene.control;

import models.User;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.windows.appointments.ShowTypesWindow;
import client.controllers.windows.contacts.NewContactAddressBookCommand;
import client.controllers.windows.contacts.NewContactWindowCommand;
import client.controllers.windows.core.ApplicationExitCommand;
import client.controllers.windows.core.ShowAboutWindowCommand;
import client.controllers.windows.core.StatsWindowCommand;
import client.controllers.windows.staff.NewServiceProviderFormCommand;
import client.controllers.windows.staff.ShowStaffAddressBookWindowCommand;
import dao.DAO;
import dao.events.UpdatedEvent;
import dao.events.UserUpdatedListener;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;


public class MainMenuBar extends MenuBar {

    private final Menu userMenu = new Menu();

    public MainMenuBar() {
        DAO.getInstance().getUserDAO().addUpdatedEventLister(onUserChangeEvent());
        init();
    }

    private UserUpdatedListener onUserChangeEvent() {
        return new UserUpdatedListener() {
            @Override
            public void updated(UpdatedEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        User u = DAO.getInstance().getUserDAO().getUser();
                        if (u == null) {
                            getMenus().remove(userMenu);
                        } else {
                            userMenu.setText(u.getName() + " " + u.getSurname());
                            getMenus().add(userMenu);
                        }
                    }
                });
            }
        };
    }

    private void init() {
        buildFileMenu();
        buildContactMenu();
        buildStaffMenu();
        buildTypesMenu();
    }

    private void buildTypesMenu() {
        Menu typeMenu = new Menu("Appointments");
        MenuItem typesMenu = new MenuItem("Manage Types");
        typeMenu.getItems().addAll(new SeparatorMenuItem(), typesMenu);
        getMenus().add(typeMenu);
        typesMenu.setOnAction(new ActionEventStrategy(new ShowTypesWindow()));
    }

    private void buildFileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem aboutMenuItem = new MenuItem("About");
        MenuItem statsMenuItem = new MenuItem("Stats");
        MenuItem exitMenuItem = new MenuItem("Quit");
        fileMenu.getItems().addAll(aboutMenuItem, new SeparatorMenuItem(), statsMenuItem, new SeparatorMenuItem(), exitMenuItem);
        getMenus().add(fileMenu);

        exitMenuItem.setOnAction(new ActionEventStrategy(new ApplicationExitCommand()));
        aboutMenuItem.setOnAction(new ActionEventStrategy(new ShowAboutWindowCommand()));
        statsMenuItem.setOnAction(new ActionEventStrategy(new StatsWindowCommand()));
    }

    private void buildContactMenu() {
        Menu contactMenu = new Menu("Contacts");
        MenuItem contactAddressBookMenuItem = new MenuItem("Address Book");
        MenuItem newContactMenuItem = new MenuItem("New Contact");
        contactMenu.getItems().addAll(contactAddressBookMenuItem, new SeparatorMenuItem(), newContactMenuItem);
        getMenus().add(contactMenu);
        contactAddressBookMenuItem.setOnAction(new ActionEventStrategy(new NewContactAddressBookCommand()));
        newContactMenuItem.setOnAction(new ActionEventStrategy(new NewContactWindowCommand()));
    }

    private void buildStaffMenu() {
        Menu staffMenu = new Menu("Staff");
        MenuItem staffAddressBookMenuItem = new MenuItem("Staff Listing");
        MenuItem newStaffMemberMenuItem = new MenuItem("New Staff Member");
        staffMenu.getItems().addAll(staffAddressBookMenuItem, new SeparatorMenuItem(), newStaffMemberMenuItem);
        getMenus().add(staffMenu);

        staffAddressBookMenuItem.setOnAction(ActionEventStrategy.create(new ShowStaffAddressBookWindowCommand()));
        newStaffMemberMenuItem.setOnAction(ActionEventStrategy.create(new NewServiceProviderFormCommand()));
    }
}
