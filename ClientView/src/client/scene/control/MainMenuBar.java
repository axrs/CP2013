package client.scene.control;

import client.controllers.adapters.ActionEventStrategy;
import client.controllers.windows.appointments.ShowTypesWindow;
import client.controllers.windows.contacts.NewContactAddressBookCommand;
import client.controllers.windows.contacts.NewContactWindowCommand;
import client.controllers.windows.core.ApplicationExitCommand;
import client.controllers.windows.core.ShowAboutWindowCommand;
import client.controllers.windows.core.StatsWindowCommand;
import client.controllers.windows.staff.NewServiceProviderFormCommand;
import client.controllers.windows.staff.ShowAboutStaffCommand;
import client.controllers.windows.staff.ShowStaffAddressBookWindowCommand;
import dao.DAO;
import dao.events.UpdatedEvent;
import dao.events.UserUpdatedListener;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import models.User;


public class MainMenuBar extends MenuBar {

    private final Menu userMenu = new Menu();
    private final MenuItem aboutStaffMenuItem = new MenuItem("View Staff");
    private final MenuItem aboutMenuItem = new MenuItem("About");
    private final MenuItem statsMenuItem = new MenuItem("Stats");
    private final MenuItem exitMenuItem = new MenuItem("Quit");
    private final Menu typeMenu = new Menu("Appointments");
    private final MenuItem typesMenu = new MenuItem("Manage Types");
    private final Menu staffMenu = new Menu("Staff");
    private final MenuItem staffAddressBookMenuItem = new MenuItem("Staff Listing");
    private final MenuItem newStaffMemberMenuItem = new MenuItem("New Staff Member");
    private final Menu contactMenu = new Menu("Contacts");
    private final MenuItem contactAddressBookMenuItem = new MenuItem("Address Book");
    private final MenuItem newContactMenuItem = new MenuItem("New Contact");

    public MainMenuBar() {
        DAO.getInstance().getUserDAO().addUpdatedEventLister(onUserChangeEvent());
        init();
    }

<<<<<<< HEAD
=======
    public MainMenuBar(boolean isAdmin) {
        if (isAdmin) {
            DAO.getInstance().getUserDAO().addUpdatedEventLister(onUserChangeEvent());
            init();
        } else {
            DAO.getInstance().getUserDAO().addUpdatedEventLister(onUserChangeEvent());
            buildUserFileMenu();
            buildUserStaffMenu();
        }
    }

>>>>>>> 1e17fc681290a503a86c4f5a33175c6c4b53b25e
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
<<<<<<< HEAD
                        } else if (u.getAdmin() <= 0 ) {
                            getMenus().remove(statsMenuItem);
                            getMenus().remove(typeMenu);
                            staffMenu.getItems().remove(staffAddressBookMenuItem);
                            staffMenu.getItems().remove(newStaffMemberMenuItem);
                            getMenus().remove(contactMenu);
                        }
                        else {
=======
                        } else if (u.getAdmin() < 0) {

                        } else {
>>>>>>> 1e17fc681290a503a86c4f5a33175c6c4b53b25e
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
        typeMenu.getItems().addAll(new SeparatorMenuItem(), typesMenu);
        getMenus().add(typeMenu);
        typesMenu.setOnAction(new ActionEventStrategy(new ShowTypesWindow()));
    }

    private void buildFileMenu() {
        Menu fileMenu = new Menu("File");

        fileMenu.getItems().addAll(aboutMenuItem, new SeparatorMenuItem(), statsMenuItem, new SeparatorMenuItem(), exitMenuItem);
        getMenus().add(fileMenu);

        exitMenuItem.setOnAction(new ActionEventStrategy(new ApplicationExitCommand()));
        aboutMenuItem.setOnAction(new ActionEventStrategy(new ShowAboutWindowCommand()));
        statsMenuItem.setOnAction(new ActionEventStrategy(new StatsWindowCommand()));
    }

    private void buildContactMenu() {

        contactMenu.getItems().addAll(contactAddressBookMenuItem, new SeparatorMenuItem(), newContactMenuItem);
        getMenus().add(contactMenu);
        contactAddressBookMenuItem.setOnAction(new ActionEventStrategy(new NewContactAddressBookCommand()));
        newContactMenuItem.setOnAction(new ActionEventStrategy(new NewContactWindowCommand()));
    }

    private void buildStaffMenu() {


        staffMenu.getItems().addAll(staffAddressBookMenuItem, new SeparatorMenuItem(), newStaffMemberMenuItem, new SeparatorMenuItem(), aboutStaffMenuItem);
        getMenus().add(staffMenu);

        staffAddressBookMenuItem.setOnAction(ActionEventStrategy.create(new ShowStaffAddressBookWindowCommand()));
        newStaffMemberMenuItem.setOnAction(ActionEventStrategy.create(new NewServiceProviderFormCommand()));
        aboutStaffMenuItem.setOnAction(ActionEventStrategy.create(new ShowAboutStaffCommand()));
    }
}
