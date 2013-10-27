package client.scene;

import javafx.application.Platform;
import jfxtras.labs.dialogs.MonologFX;

public abstract class CoreStage extends NotifiableStage {

    @Override
    public void onInformation(final String message) {
        try {
            information(message);
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    information(message);
                }
            });
        }
    }

    @Override
    public void onSuccess() {
        try {
            success();
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    success();
                }
            });
        }
    }

    @Override
    public void onValidationError(final String message) {

        try {
            validationError(message);
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    validationError(message);
                }
            });
        }
    }

    @Override
    public void onError(final String message) {
        try {
            error(message);
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    error(message);
                }
            });
        }
    }

    public void information(String message) {
        MonologFX infoDialog = new MonologFX(MonologFX.Type.INFO);
        infoDialog.setMessage(message);
        infoDialog.setModal(true);
        infoDialog.showDialog();
    }

    public void error(String message) {
        MonologFX infoDialog = new MonologFX(MonologFX.Type.ERROR);
        infoDialog.setMessage(message);
        infoDialog.setModal(true);
        infoDialog.showDialog();
    }

    public abstract void validationError(String message);

    public abstract void success();
}
