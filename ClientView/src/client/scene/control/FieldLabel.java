package client.scene.control;

import javafx.scene.Node;

public class FieldLabel extends ClassLabel {
    public FieldLabel() {
        setClass("label-field");
    }

    public FieldLabel(String s) {
        super(s);
        setClass("label-field");
    }

    public FieldLabel(String s, Node node) {
        super(s, node);
        setClass("label-field");
    }
}
