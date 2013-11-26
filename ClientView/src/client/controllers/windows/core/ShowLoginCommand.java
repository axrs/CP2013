package client.controllers.windows.core;

import client.controllers.ICommand;
import client.stages.LoginWindow;

public class ShowLoginCommand implements ICommand {
    @Override
    public void execute() {
        LoginWindow w = new LoginWindow();
        w.show();
    }
}
