package client.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class SloganLabel extends Label {
    public SloganLabel() {
        setClass();
    }

    public SloganLabel(String s) {
        super(s);
        setClass();
    }

    public SloganLabel(String s, Node node) {
        super(s, node);
        setClass();
    }

    private void setClass() {
        this.getStyleClass().add("label-slogan");
    }
}
