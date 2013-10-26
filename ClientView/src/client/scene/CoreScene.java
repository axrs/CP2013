package client.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

public class CoreScene extends Scene {

    public CoreScene(Parent parent) {
        super(parent);
        loadStyles();
    }

    public CoreScene(Parent parent, double v, double v2) {
        super(parent, v, v2);
        loadStyles();
    }

    public CoreScene(Parent parent, Paint paint) {
        super(parent, paint);
        loadStyles();
    }

    public CoreScene(Parent parent, double v, double v2, Paint paint) {
        super(parent, v, v2, paint);
        loadStyles();
    }

    public CoreScene(Parent parent, double v, double v2, boolean b) {
        super(parent, v, v2, b);
        loadStyles();
    }

    private void loadStyles() {
        this.getStylesheets().add("./styles/core.css");
    }
}
