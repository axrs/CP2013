package client.controllers.windows.contacts;

import Models.ServiceProvider;
import client.controllers.ICommand;
import client.stages.staff.FormView;


public class EditStaffWindowCommand implements ICommand {
    ServiceProvider provider = null;

    public EditStaffWindowCommand(ServiceProvider provider) {
        this.provider = provider;
    }

    @Override
    public void execute() {
        FormView serviceProviderFormUI = new FormView(provider);
        serviceProviderFormUI.show();
    }
}
