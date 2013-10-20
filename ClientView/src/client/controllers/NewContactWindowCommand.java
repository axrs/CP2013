package client.controllers;

import client.ContactFormView;

public class NewContactWindowCommand extends NewStage implements ICommand {

    @Override
    public void execute() {
        new ContactFormView().show();
    }
}
