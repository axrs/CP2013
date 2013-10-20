package client.scene.control;

import javafx.scene.Node;

public class HeaderLabel extends ClassLabel {
    public HeaderLabel() {
        setClass("label-header");
    }

    public HeaderLabel(String s) {
        super(s);
        setClass("label-header");
    }

    public HeaderLabel(String s, Node node) {
        super(s, node);
        setClass("label-header");
    }
}
