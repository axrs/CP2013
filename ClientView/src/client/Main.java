package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.Agenda;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        primaryStage.setTitle("CP2013 Appointment Scheduler");

        BorderPane mainPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu contactMenu = new Menu("Contacts");
        Menu staffMenu = new Menu("Staff");

        MenuItem exitMenuItem = new MenuItem("Quit");

        MenuItem contactAddressBookMenuItem = new MenuItem("Address Book");
        MenuItem newContactMenuItem = new MenuItem("New Contact");

        MenuItem staffAddressBookMenuItem = new MenuItem("Staff Listing");
        MenuItem newStaffMemberMenuItem = new MenuItem("New Staff Member");

        MenuItem aboutMenuItem = new MenuItem("About");

        fileMenu.getItems().addAll(aboutMenuItem, new SeparatorMenuItem(), exitMenuItem);
        contactMenu.getItems().addAll(contactAddressBookMenuItem, newContactMenuItem);
        staffMenu.getItems().addAll(staffAddressBookMenuItem, newStaffMemberMenuItem);
        menuBar.getMenus().addAll(fileMenu, contactMenu, staffMenu);

        mainPane.setTop(menuBar);

        exitMenuItem.setOnAction(onMenuQuitClick(primaryStage));

        aboutMenuItem.setOnAction(onMenuAboutClick());


        contactAddressBookMenuItem.setOnAction(onContactAddressBookMenuClick());

        newContactMenuItem.setOnAction(onNewContactMenuClick());

        staffAddressBookMenuItem.setOnAction(onStaffAddressBookMenuClick());

        newStaffMemberMenuItem.setOnAction(onNewStaffMenuClick());


        Agenda agenda = new Agenda();
        mainPane.setCenter(agenda);

        agenda.appointments();

        Scene scene = new Scene(mainPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private EventHandler<ActionEvent> onNewStaffMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ServiceProviderFormUI serviceProviderFormUI = new ServiceProviderFormUI();
                Stage stage = new Stage();
                try {
                    serviceProviderFormUI.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        };
    }

    private EventHandler<ActionEvent> onStaffAddressBookMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AdminUI adminUI = new AdminUI();
                Stage adminStage = new Stage();
                try {
                    adminUI.start(adminStage);
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        };
    }

    private EventHandler<ActionEvent> onNewContactMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ContactFormUI userUI = new ContactFormUI();
                Stage userStage = new Stage();
                try {
                    userUI.start(userStage);
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        };
    }

    private EventHandler<ActionEvent> onContactAddressBookMenuClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ContactUI contactUI = new ContactUI();
                Stage userStage = new Stage();
                try {
                    contactUI.start(userStage);
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        };
    }

    private EventHandler<ActionEvent> onMenuAboutClick() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage aboutStage = new Stage();
                aboutStage.setTitle("About Peeps");

                BorderPane borderPane = new BorderPane();
                Label aboutText = new Label("Shear-n-dipity does haircuts and things like that\n" +
                        " Get your hair cut now");

                borderPane.setCenter(aboutText);

                aboutStage.setScene(new Scene(borderPane));
                aboutStage.show();
            }
        };
    }

    private EventHandler<ActionEvent> onMenuQuitClick(final Stage primaryStage) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close();
                Platform.exit();
            }
        };
    }
}
