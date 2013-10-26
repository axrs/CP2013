package client.stages;

import client.controllers.windows.core.ApplicationExitCommand;
import client.controllers.windows.core.CloseStageCommand;
import client.controllers.CompositeCommand;
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
import javafx.scene.layout.GridPane;
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

        BorderPane borderPane = new BorderPane();

        TextField userName = new TextField();
        TextField password = new TextField();
        Button gitLogin = new Button("Login With GitHub");

        CompositeCommand cmds = new CompositeCommand();
        cmds.addCommand(new GitLoginWindow());
        cmds.addCommand(new CloseStageCommand(this));

        gitLogin.setOnAction(new ActionEventStrategy(cmds));

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid");
        gridPane.addRow(0, new Label("Username: "), userName);
        gridPane.addRow(1, new Label("Password: "), password);

        Button login = new Button("Login");
        gridPane.add(login, 1, 2);
        login.setOnAction(login());


        ActionButtons buttons = new ActionButtons(false);
        buttons.setOnCloseAction(ActionEventStrategy.create(new ApplicationExitCommand()));
        buttons.addControl(gitLogin);

        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttons);

        setScene(new CoreScene(borderPane));
    }

    private EventHandler<ActionEvent> login() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

}
