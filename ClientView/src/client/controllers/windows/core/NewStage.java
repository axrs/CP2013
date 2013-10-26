package client.controllers.windows.core;

import javafx.application.Application;
import javafx.stage.Stage;

public class NewStage {
    void tryStageStart(Application window) {
        try {
            window.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
