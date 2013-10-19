package client.controllers;

import client.ServiceProviderFormUI;
import client.controllers.ICommand;
import client.controllers.NewStage;


public class NewServiceProviderFormCommand extends NewStage implements ICommand {
    @Override
    public void execute() {

        tryStageStart(new ServiceProviderFormUI());

    }
}
