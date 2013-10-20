package client.scene.control;

import javafx.scene.Node;

public class SloganLabel extends ClassLabel {
    public SloganLabel() {
        setClass("label-slogan");
    }

    public SloganLabel(String s) {
        super(s);
        setClass("label-slogan");
    }

    public SloganLabel(String s, Node node) {
        super(s, node);
        setClass("label-slogan");
    }

}
