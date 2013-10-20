package client.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class ClassLabel extends Label {
    public ClassLabel() {
    }

    public ClassLabel(String s) {
        super(s);
    }

    public ClassLabel(String s, Node node) {
        super(s, node);
    }

    void setClass(String style) {
        this.getStyleClass().add(style);
    }
}
