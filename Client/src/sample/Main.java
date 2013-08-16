package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.Agenda;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

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
                Label aboutText = new Label("Sheer'n'dipity does haircuts and things like that\n" +
                        " Get your hair cut now");

                borderPane.setCenter(aboutText);

                aboutStage.setScene(new Scene(borderPane));
                aboutStage.show();
            }
        });

        Agenda agenda = new Agenda();
        mainPane.setCenter(agenda);

        agenda.appointments();
        /* final GridPane grid = new GridPane();
        TabPane mainPane = new TabPane();

        Tab userTab = new Tab();
        userTab.setText("User");


        Tab adminTab = new Tab();
        adminTab.setText("Admin");

        Tab contactTab = new Tab();
        contactTab.setText("Contacts");

        Tab aboutTab= new Tab();
        aboutTab.setText("About");
        Label aboutText = new Label();
        aboutText.setText("Sheer'n'dipity does haircuts and things like that\n Get your hair cut now");
        aboutTab.setContent(aboutText);

        mainPane.getTabs().add(userTab);
        mainPane.getTabs().add(adminTab);
        mainPane.getTabs().add(contactTab);
        mainPane.getTabs().add(aboutTab);

        grid.add(mainPane,0,0);
        Button userBtn = new Button("User");
        grid.add(userTab,0,0);

        Button adminBtn = new Button("Admin");
        grid.add(adminBtn,0,1);

        Button aboutBtn = new Button("About");
        grid.add(aboutBtn,0,2);

        Button contactBtn = new Button("Contact");
        grid.add(contactBtn,0,3); */

        Scene scene = new Scene(mainPane,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
