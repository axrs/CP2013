package client.controllers;

import client.stages.staff.FormView;


public class NewServiceProviderFormCommand implements ICommand {
    @Override
    public void execute() {
        new FormView().show();
    }
}
