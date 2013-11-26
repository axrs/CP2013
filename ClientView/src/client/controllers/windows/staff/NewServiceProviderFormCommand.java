package client.controllers.windows.staff;

import client.controllers.ICommand;
import client.stages.staff.FormView;


public class NewServiceProviderFormCommand implements ICommand {
    @Override
    public void execute() {
        new FormView().show();
    }
}
