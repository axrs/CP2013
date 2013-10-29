package client.scene.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ActionButtons extends BorderPane {

    final Button submitButton = new Button("Save");
    final Button closeButton = new Button("Close");
    private final HBox rightPane = new HBox();
    private final HBox leftPane = new HBox();
    Boolean showSaveButton = false;

    public ActionButtons() {
        init();
        setShowSaveButton(false);
    }

    public ActionButtons(boolean showSave) {
        init();
        setShowSaveButton(showSave);
    }

    public void setSaveText(String value) {
        this.submitButton.setText(value);
    }

    public void setCloseText(String value) {
        closeButton.setText(value);
    }

    public void addControl(Control c) {
        rightPane.getChildren().add(c);
    }

    public void addLeftControl(Control c) {
        leftPane.getChildren().add(c);
    }

    public void removeControl(Control c) {
        if (rightPane.getChildren().contains(c)) {
            rightPane.getChildren().remove(c);
        }
    }

    public void removeLeftControl(Control c) {
        if (leftPane.getChildren().contains(c)) {
            leftPane.getChildren().remove(c);
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
        rightPane.setAlignment(Pos.BASELINE_RIGHT);
        rightPane.getChildren().add(closeButton);
        closeButton.setCancelButton(true);
        this.setRight(rightPane);
        this.setLeft(leftPane);
    }

    public void setShowSaveButton(Boolean showSaveButton) {
        this.showSaveButton = showSaveButton;
        if (showSaveButton && !this.getChildren().contains(submitButton)) {
            rightPane.getChildren().add(submitButton);
            //closeButton.setDefaultButton(false);
        } else {
            rightPane.getChildren().remove(submitButton);
            //closeButton.setDefaultButton(true);
        }
    }
}
