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
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("CP2013 Appointment Scheduler");

        BorderPane mainPane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Actions");
        MenuItem userMenu = new MenuItem("User");
        MenuItem adminMenu = new MenuItem("Admin");
        MenuItem contactMenu = new MenuItem("Contact");
        MenuItem aboutMenu = new MenuItem("About");

        menu.getItems().add(userMenu);
        menu.getItems().add(adminMenu);
        menu.getItems().add(contactMenu);
        menu.getItems().add(aboutMenu);
        menuBar.getMenus().add(menu);

        mainPane.setTop(menuBar);

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

        userMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UserUI userUI = new UserUI();
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

        contactMenu.setOnAction(new EventHandler<ActionEvent>() {
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

        Agenda agenda = new Agenda();
        mainPane.setCenter(agenda);

        agenda.appointments();

        Scene scene = new Scene(mainPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
