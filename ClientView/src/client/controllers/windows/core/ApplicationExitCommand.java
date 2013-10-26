package client.controllers.windows.core;

import client.controllers.ICommand;
import javafx.application.Platform;

public class ApplicationExitCommand implements ICommand {

    @Override
    public void execute() {
        Platform.exit();
        System.exit(0);
    }
}
