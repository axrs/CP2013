package client.controllers;

import client.stages.contacts.FormView;

public class NewContactWindowCommand extends NewStage implements ICommand {

    @Override
    public void execute() {
        new FormView().show();
    }
}
