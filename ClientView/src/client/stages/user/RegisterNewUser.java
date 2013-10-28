package client.stages.user;

import Models.User;
import client.controllers.ICommand;
import client.controllers.adapters.WindowEventStrategy;
import client.controllers.models.CreateUserCommand;
import client.controllers.windows.core.CloseStageCommand;
import client.scene.CoreScene;
import client.scene.CoreStage;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;
import jfxtras.labs.scene.control.BeanPathAdapter;

public class RegisterNewUser extends CoreStage {
    final TextField name = new TextField();
    final TextField surname = new TextField();
    final TextField company = new TextField();
    final TextField phone = new TextField();
    final TextField email = new TextField();
    final TextField address = new TextField();
    final TextField suburb = new TextField();
    final TextField city = new TextField();
    final TextField post = new TextField();
    final TextField state = new TextField();
    final TextField userName = new TextField();
    final PasswordField password = new PasswordField();
    final PasswordField confirmPassword = new PasswordField();
    final ActionButtons buttons = new ActionButtons(true);
    boolean isDirty = false;
    private User user = new User();
    private RegisterNewUser instance = this;
    private ICommand onFailure = null;
    private BeanPathAdapter<User> userPA;


    public RegisterNewUser() {
        setTitle("Create an Account");
        init();
    }

    public RegisterNewUser(User user) {
        setTitle("Update Account Details");
        this.user = user;
        init();
    }

    private void init() {
        BorderPane border = new BorderPane();
        border.setCenter(setupFormInputs());
        border.setBottom(buttons);

        buttons.setOnCloseAction(onCloseAction());
        buttons.setOnSaveAction(onSaveAction());

        Scene scene = new CoreScene(border);
        setOnCloseRequest(new WindowEventStrategy(new CloseStageCommand(this)));
        setResizable(false);
        setScene(scene);
        bindToModel();
    }

    private void bindToModel() {
        userPA = new BeanPathAdapter<User>(user);
        userPA.bindBidirectional("name", name.textProperty());
        userPA.bindBidirectional("surname", surname.textProperty());
        userPA.bindBidirectional("company", company.textProperty());
        userPA.bindBidirectional("phone", phone.textProperty());
        userPA.bindBidirectional("email", email.textProperty());

        userPA.bindBidirectional("address", address.textProperty());
        userPA.bindBidirectional("suburb", suburb.textProperty());
        userPA.bindBidirectional("city", city.textProperty());
        userPA.bindBidirectional("post", post.textProperty());
        userPA.bindBidirectional("state", state.textProperty());

        userPA.bindBidirectional("userName", userName.textProperty());
        userPA.bindBidirectional("password", password.textProperty());
    }

    @Override
    public void validationError(String message) {
        onInformation(message);
    }

    @Override
    public void close() {
        if (onFailure != null) {
            onFailure.execute();
        }
        super.close();
    }

    @Override
    public void success() {
        this.isDirty = false;
        this.onFailure = null;
        this.close();
    }

    public GridPane setupFormInputs() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("grid");

        grid.addRow(0, LabelFactory.createFieldLabel("Username:"), userName);
        grid.addRow(1, LabelFactory.createFieldLabel("First Name:"), name);
        grid.addRow(2, LabelFactory.createFieldLabel("Last Name:"), surname);
        grid.addRow(3, new Separator(), new Separator());
        grid.addRow(4, LabelFactory.createFieldLabel("Password:"), password);
        grid.addRow(5, LabelFactory.createFieldLabel("Confirm Password:"), confirmPassword);
        grid.addRow(6, new Separator(), new Separator());
        grid.addRow(7, LabelFactory.createFieldLabel("Company:"), company);
        grid.addRow(8, LabelFactory.createFieldLabel("Phone:"), phone);
        grid.addRow(9, LabelFactory.createFieldLabel("Email:"), email);
        grid.addRow(10, new Separator(), new Separator());
        grid.addRow(11, LabelFactory.createFieldLabel("Street:"), address);
        grid.addRow(12, LabelFactory.createFieldLabel("Suburb:"), suburb);
        grid.addRow(13, LabelFactory.createFieldLabel("City:"), city);
        grid.addRow(14, LabelFactory.createFieldLabel("Post Code:"), post);
        grid.addRow(15, LabelFactory.createFieldLabel("State:"), state);

        for (Node n : grid.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).setPrefWidth(200);
                ((TextField) n).setPrefHeight(20);
            }
            if (n instanceof Label) {
                ((Label) n).setPrefWidth(120);
                ((Label) n).setPrefHeight(20);
            }
        }

        grid.addEventFilter(KeyEvent.KEY_RELEASED, setDirty());

        return grid;
    }

    private EventHandler<ActionEvent> onSaveAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boolean isValid = true;

                if (!validatePasswordLength()) {
                    isValid = false;
                    onInformation("Your password must be a minimum of 8 characters.");
                }

                if (!validateWithConfirmationPassword()) {
                    isValid = false;
                    onInformation("Your passwords do not match.");
                }

                if (isValid) {
                    new CreateUserCommand(user, instance).execute();
                }
            }
        };
    }

    private void tryClose() {
        if (isDirty) {
            MonologFX dialog = new MonologFX(MonologFX.Type.QUESTION);
            dialog.setMessage("There are changes pending on this form.  Are you sure you wish to close?");
            dialog.setTitleText("Confirm Cancellation");
            dialog.setModal(true);
            MonologFXButton.Type type = dialog.showDialog();
            if (type == MonologFXButton.Type.YES) {
                this.close();
            }
        } else {
            this.close();
        }
    }

    private EventHandler<ActionEvent> onCloseAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tryClose();
            }
        };
    }

    private EventHandler<KeyEvent> setDirty() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getTarget() instanceof TextField) {
                    isDirty = true;
                }
            }
        };
    }

    private boolean validatePasswordLength() {
        return !(password.getText().length() < 8);
    }

    private boolean validateWithConfirmationPassword() {
        return (confirmPassword.getText().equals(password.getText()));
    }
}