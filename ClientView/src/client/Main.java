package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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

        MenuItem quitMenu = new MenuItem("Quit");

        MenuItem addressBookMenu = new MenuItem("Address Book");
        MenuItem newContactMenu = new MenuItem("New Contact");

        MenuItem adminMenu = new MenuItem("Staff Listing");
        MenuItem newServiceProviderMenu = new MenuItem("New Staff Member");

        MenuItem aboutMenu = new MenuItem("About");

        fileMenu.getItems().add(quitMenu);
        fileMenu.getItems().add(0,aboutMenu);
        contactMenu.getItems().add(addressBookMenu);
        contactMenu.getItems().add(newContactMenu);
        staffMenu.getItems().add(adminMenu);
        staffMenu.getItems().add(newServiceProviderMenu);
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(contactMenu);
        menuBar.getMenus().add(staffMenu);

        mainPane.setTop(menuBar);

        quitMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close();
            }
        });

        aboutMenu.setOnAction(new EventHandler<ActionEvent>() {
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
        });


        addressBookMenu.setOnAction(new EventHandler<ActionEvent>() {
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
        });

        newContactMenu.setOnAction(new EventHandler<ActionEvent>() {
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
        });

        adminMenu.setOnAction(new EventHandler<ActionEvent>() {
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
        });

        newServiceProviderMenu.setOnAction(new EventHandler<ActionEvent>() {
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
        });


        Agenda agenda = new Agenda();
        mainPane.setCenter(agenda);

        agenda.appointments();

        Scene scene = new Scene(mainPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
