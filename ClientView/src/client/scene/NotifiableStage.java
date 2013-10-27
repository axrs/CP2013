package client.scene;

import javafx.stage.Stage;


public abstract class NotifiableStage extends Stage implements INotifiable {

    @Override
    public void onInformation(String message) {

    }

    @Override
    public void onValidationError(String message) {

    }

    @Override
    public void onError(String message) {

    }
}
