package client.stages.contacts;

import client.controllers.adapters.ActionEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 26/11/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterNewUser extends Stage {

    public RegisterNewUser() {
        setTitle("CP2013 Appointment Scheduler - Register New User");

        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid");

        TextField foreName = new TextField();
        TextField surName = new TextField();
        TextField username = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();

        gridPane.addColumn(0, new Label("First Name:"), new Label("Last Name:"), new Label("Username:"), new Label("Password:"),
                    new Label("Confirm Password:"));
        gridPane.addColumn(1,foreName, surName, username, passwordField, confirmPasswordField);

        ActionButtons buttons = new ActionButtons(true);
        buttons.setOnCloseAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
            }
        });
        buttons.setOnSaveAction(registerNewUser());

        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttons);



        setScene(new CoreScene(borderPane));
    }

    private EventHandler<ActionEvent> registerNewUser() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
