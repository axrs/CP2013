package client.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class HeaderLabel extends Label {
    public HeaderLabel() {
        setClass();
    }

    public HeaderLabel(String s) {
        super(s);
        setClass();
    }

    public HeaderLabel(String s, Node node) {
        super(s, node);
        setClass();
    }

    private void setClass() {
        this.getStyleClass().add("label-header");
    }
}
