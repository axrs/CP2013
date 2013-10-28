package client.stages;

import client.controllers.ShowRegisterCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.controllers.adapters.WindowEventStrategy;
import client.controllers.models.LoginUserCommand;
import client.controllers.windows.core.ApplicationExitCommand;
import client.scene.CoreScene;
import client.scene.CoreStage;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import dao.DAO;
import dao.events.UpdatedEvent;
import dao.events.UserUpdatedListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;


public class LoginWindow extends CoreStage {
    private TextField userName = new TextField();
    private TextField password = new PasswordField();

    public LoginWindow() {
        setTitle("CP2013 Appointment Scheduler - Login");
        initModality(Modality.APPLICATION_MODAL);
        setOnCloseRequest(WindowEventStrategy.create(new ApplicationExitCommand()));

        BorderPane borderPane = new BorderPane();
        Button gitLogin = new Button("Login With GitHub");

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid");
        gridPane.addRow(0, LabelFactory.createFieldLabel("Username"), userName);
        gridPane.addRow(1, LabelFactory.createFieldLabel("Password"), password);

        Button login = new Button("Login");
        Button register = new Button("Register");
        gridPane.addRow(2, register, login);
        login.setDefaultButton(true);

        ActionButtons buttons = new ActionButtons(false);
        buttons.setOnCloseAction(ActionEventStrategy.create(new ApplicationExitCommand()));
        buttons.addControl(gitLogin);

        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttons);

        setScene(new CoreScene(borderPane));


        register.setOnAction(new ActionEventStrategy(new ShowRegisterCommand()));
        gitLogin.setOnAction(new ActionEventStrategy(new GitLoginWindow()));

        login.setOnAction(onLoginAction());
        /**
         * Listens to the UserDAO for a CurrentUserChange event.
         */
        DAO.getInstance().getUserDAO().addUpdatedEventLister(new UserUpdatedListener() {
            @Override
            public void updated(UpdatedEvent event) {
                onSuccess();
            }
        });
    }

    private void tryLogin() {
        new LoginUserCommand(userName.getText(), password.getText(), this).execute();
    }

    private EventHandler<ActionEvent> onLoginAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tryLogin();
            }
        };
    }

    @Override
    public void validationError(String message) {
    }

    @Override
    public void success() {
        if (DAO.getInstance().getUserDAO().getUser() != null) {
            this.close();
        }
    }
}
