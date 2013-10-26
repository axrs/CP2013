package client.scene.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;

public class ActionButtons extends HBox {

    final Button submitButton = new Button("Save");
    final Button closeButton = new Button("Close");
    Boolean showSaveButton = false;

    public ActionButtons() {
        init();
        setShowSaveButton(false);
    }

    public ActionButtons(boolean showSave) {
        init();
        setShowSaveButton(showSave);
    }

    public void addControl(Control c) {
        this.getChildren().add(c);
    }

    public void removeControl(Control c) {
        if (this.getChildren().contains(c)) {
            this.getChildren().remove(c);
        }
    }

    public Button getSaveButton() {
        return submitButton;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public void setOnSaveAction(EventHandler<ActionEvent> action) {
        submitButton.setOnAction(action);
    }

    public void setOnCloseAction(EventHandler<ActionEvent> action) {
        closeButton.setOnAction(action);
    }

    public void addSaveActionHandler(EventHandler<ActionEvent> handler) {
        submitButton.addEventHandler(ActionEvent.ACTION, handler);
    }

    public void removeSaveActionHandler(EventHandler<ActionEvent> handler) {
        submitButton.removeEventHandler(ActionEvent.ACTION, handler);
    }

    public void addCloseActionHandler(EventHandler<ActionEvent> handler) {
        closeButton.addEventHandler(ActionEvent.ACTION, handler);
    }

    public void removeCloseActionHandler(EventHandler<ActionEvent> handler) {
        closeButton.removeEventHandler(ActionEvent.ACTION, handler);
    }

    private void init() {
        this.getStyleClass().add("button_box");
        this.setAlignment(Pos.BASELINE_RIGHT);
        this.getChildren().add(closeButton);
        closeButton.setCancelButton(true);
        submitButton.setDefaultButton(true);

    }

    public void setShowSaveButton(Boolean showSaveButton) {
        this.showSaveButton = showSaveButton;
        if (showSaveButton && !this.getChildren().contains(submitButton)) {
            this.getChildren().add(submitButton);
            closeButton.setDefaultButton(false);
        } else {
            this.getChildren().remove(submitButton);
            closeButton.setDefaultButton(true);
        }
    }
}
