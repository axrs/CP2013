package client.stages;

import client.controllers.ApplicationExitCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.adapters.WindowEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 26/11/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginWindow extends Stage {

    public LoginWindow() {
        setTitle("CP2013 Appointment Scheduler - Login");
        initModality(Modality.APPLICATION_MODAL);
        setOnCloseRequest(WindowEventStrategy.create(new ApplicationExitCommand()));

        Button login = new Button("Login");
        ActionButtons buttons = new ActionButtons(false);
        buttons.addControl(login);
        buttons.setOnCloseAction(ActionEventStrategy.create(new ApplicationExitCommand()));

        BorderPane borderPane = new BorderPane();

        TextField userName = new TextField();
        TextField password = new TextField();
        Button gitLogin = new Button("Login With GitHub");
        gitLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new GitLoginWindow().show();
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(new Label("Username: "), userName, new Label("Password: "), password, gitLogin);
        hBox.getStyleClass().add("grid");

        borderPane.setCenter(hBox);
        borderPane.setBottom(buttons);

        setScene(new CoreScene(borderPane));
    }

}
