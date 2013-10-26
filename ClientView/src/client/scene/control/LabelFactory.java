package client.scene.control;

import javafx.scene.control.Label;

public class LabelFactory {

    public static Label createHeadingLabel(String content) {
        Label l = new Label(content);
        l.getStyleClass().add("label-header");
        return l;
    }

    public static Label createSloganLabel(String content){
        Label l = new Label(content);
        l.getStyleClass().add("label-slogan");
        return l;
    }

    public static Label createFieldLabel(String content){
        Label l = new Label(content);
        l.getStyleClass().add("label-field");
        return l;
    }
}
