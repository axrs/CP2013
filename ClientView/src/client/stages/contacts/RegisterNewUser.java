package client.stages.contacts;

import client.scene.CoreScene;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 26/11/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterNewUser extends Stage {

    final TextField forenameInput = new TextField();
    final TextField surnameInput = new TextField();
    final TextField companyInput = new TextField();
    final TextField phoneInput = new TextField();
    final TextField emailInput = new TextField();
    final TextField addrStreetInput = new TextField();
    final TextField addrSuburbInput = new TextField();
    final TextField addrCityInput = new TextField();
    final TextField addrZipInput = new TextField();
    final TextField addrStateInput = new TextField();
    final TextField usernameInput = new TextField();
    final PasswordField passwordInput = new PasswordField();
    final PasswordField cPasswordInput = new PasswordField();
    final ActionButtons buttons = new ActionButtons(true);
    boolean isDirty = false;

    public RegisterNewUser() {
        setTitle("CP2013 Appointment Scheduler - New Contact");

        BorderPane border = new BorderPane();
        border.setCenter(setupFormInputs());
        border.setBottom(buttons);

        buttons.setOnCloseAction(onCloseAction());
        buttons.setOnSaveAction(onSaveAction());

        Scene scene = new CoreScene(border);
        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                close();
            }
        });
        setResizable(false);
        setScene(scene);
        show();
    }

    public GridPane setupFormInputs() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("grid");

        grid.addRow(0, LabelFactory.createFieldLabel("First Name:"), forenameInput);

        grid.addRow(1, LabelFactory.createFieldLabel("Last Name:"), surnameInput);

        grid.addRow(2, new Separator(), new Separator());

        grid.addRow(3, LabelFactory.createFieldLabel("Company:"), companyInput);

        grid.addRow(4, LabelFactory.createFieldLabel("Phone:"), phoneInput);

        grid.addRow(5, LabelFactory.createFieldLabel("Email:"), emailInput);

        grid.addRow(6, new Separator(), new Separator());

        grid.addRow(7, LabelFactory.createFieldLabel("Street:"), addrStreetInput);

        grid.addRow(8, LabelFactory.createFieldLabel("Suburb:"), addrSuburbInput);

        grid.addRow(9, LabelFactory.createFieldLabel("City:"), addrCityInput);

        grid.addRow(10, LabelFactory.createFieldLabel("Post Code:"), addrZipInput);

        grid.addRow(11, LabelFactory.createFieldLabel("State:"), addrStateInput);

        grid.addRow(12, new Separator(), new Separator());

        grid.addRow(13, LabelFactory.createFieldLabel("Username:"), usernameInput);

        grid.addRow(14, LabelFactory.createFieldLabel("Password:"), passwordInput);

        grid.addRow(15, LabelFactory.createFieldLabel("Confirm Password:"), cPasswordInput);


        for (Node n : grid.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).setPrefWidth(200);
                ((TextField) n).setPrefHeight(20);
            }
            if (n instanceof Label) {
                ((Label) n).setPrefWidth(80);
                ((Label) n).setPrefHeight(20);
            }
        }

        grid.addEventFilter(KeyEvent.KEY_RELEASED, setDirty());


        return grid;
    }

    private EventHandler<WindowEvent> onClose() {
        return new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                tryClose();
            }
        };
    }

    private EventHandler<ActionEvent> onSaveAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                isDirty = false;
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

    public void addSaveActionEventHandler(EventHandler<ActionEvent> handler) {
        buttons.addSaveActionHandler(handler);
    }

    public void onError(String message) {
        MonologFX infoDialog = new MonologFX(MonologFX.Type.INFO);
        infoDialog.setMessage(message);
        infoDialog.setModal(true);
        infoDialog.showDialog();
        isDirty = true;
    }

    private boolean isValidPassword() {
        boolean isValid = true;
        if (passwordInput.getText().length() < 8) {
            isValid = false;
        }
        if (!cPasswordInput.getText().equals(passwordInput.getText())) {
            isValid = false;
        }
        return isValid;
    }

}

