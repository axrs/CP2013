package client;

import Interfaces.ContactView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXButton;

public class ContactFormView extends Application implements ContactView {
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
    final Button submitButton = new Button("Save");
    final Button closeButton = new Button("Close");
    boolean isDirty = false;

    public ContactFormView() {
    }

    public void start(final Stage primaryStage) throws Exception {

        closeButton.setCancelButton(true);
        closeButton.setOnAction(onCloseAction());
        submitButton.setOnAction(onSaveAction());

        primaryStage.setTitle("CP2013 Appointment Scheduler - New Contact");
        BorderPane border = new BorderPane();

        border.setCenter(setupFormInputs());
        border.setBottom(setupActionButtons());

        Scene scene = new Scene(border);
        primaryStage.setOnCloseRequest(onClose());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox setupActionButtons() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #dedede;");
        hbox.setAlignment(Pos.BASELINE_RIGHT);

        submitButton.setPrefSize(80, 20);
        closeButton.setPrefSize(80, 20);
        hbox.getChildren().addAll(submitButton, closeButton);

        return hbox;
    }

    public GridPane setupFormInputs() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.addRow(0, new Label("First Name:"), forenameInput);

        grid.addRow(1, new Label("Last Name:"), surnameInput);

        grid.addRow(2, new Separator(), new Separator());

        grid.addRow(3, new Label("Company:"), companyInput);

        grid.addRow(4, new Label("Phone:"), phoneInput);

        grid.addRow(5, new Label("Email:"), emailInput);

        grid.addRow(6, new Separator(), new Separator());

        grid.addRow(7, new Label("Street:"), addrStreetInput);

        grid.addRow(8, new Label("Suburb:"), addrSuburbInput);

        grid.addRow(9, new Label("City:"), addrCityInput);

        grid.addRow(10, new Label("Post Code:"), addrZipInput);

        grid.addRow(11, new Label("State:"), addrStateInput);


        for (Node n : grid.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).setPrefWidth(200);
                ((TextField) n).setPrefHeight(20);
            } else if (n instanceof Label) {
                ((Label) n).setPrefWidth(75);
                ((Label) n).setMinWidth(75);
                ((Label) n).setTextAlignment(TextAlignment.RIGHT);
                ((Label) n).setAlignment(Pos.BASELINE_RIGHT);
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
                ((Stage) closeButton.getScene().getWindow()).close();
            }
        } else {
            ((Stage) closeButton.getScene().getWindow()).close();
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

    @Override
    public String getForename() {
        return forenameInput.getText();
    }

    @Override
    public void setForename(String forename) {
        forenameInput.setText(forename);
    }

    @Override
    public String getSurname() {
        return surnameInput.getText();
    }

    @Override
    public void setSurname(String surname) {
        surnameInput.setText(surname);
    }

    @Override
    public String getCompany() {
        return companyInput.getText();
    }

    @Override
    public void setCompany(String company) {
        companyInput.setText(company);
    }

    @Override
    public String getEmail() {
        return emailInput.getText();
    }

    @Override
    public void setEmail(String email) {
        emailInput.setText(email);
    }

    @Override
    public String getPhone() {
        return phoneInput.getText();
    }

    @Override
    public void setPhone(String phone) {
        phoneInput.setText(phone);
    }

    @Override
    public String getAddress() {
        return addrStreetInput.getText();
    }

    @Override
    public void setAddress(String address) {
        addrStreetInput.setText(address);
    }

    @Override
    public String getSuburb() {
        return addrSuburbInput.getText();
    }

    @Override
    public void setSuburb(String suburb) {
        addrSuburbInput.setText(suburb);
    }

    @Override
    public String getCity() {
        return addrCityInput.getText();
    }

    @Override
    public void setCity(String city) {
        addrCityInput.setText(city);
    }

    @Override
    public String getState() {
        return addrStateInput.getText();
    }

    @Override
    public void setState(String state) {
        addrStateInput.setText(state);
    }

    @Override
    public String getZip() {
        return addrZipInput.getText();
    }

    @Override
    public void setZip(String zip) {
        addrZipInput.setText(zip);
    }

    @Override
    public void addSaveActionEventHandler(EventHandler<ActionEvent> handler) {
        submitButton.addEventHandler(ActionEvent.ACTION, handler);
    }

    @Override
    public void onError(String message) {
        MonologFX infoDialog = new MonologFX(MonologFX.Type.INFO);
        infoDialog.setMessage(message);
        infoDialog.setModal(true);
        infoDialog.showDialog();
        isDirty = true;
    }
}
