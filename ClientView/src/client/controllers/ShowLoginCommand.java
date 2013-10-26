package client.controllers;

import client.stages.LoginWindow;

public class ShowLoginCommand implements ICommand {
    @Override
    public void execute() {
        LoginWindow w = new LoginWindow();
        w.show();
    }
}
