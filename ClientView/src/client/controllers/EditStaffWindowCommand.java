package client.controllers;

import Controllers.ServiceProviderController;
import Models.ServiceProvider;
import client.stages.staff.FormView;

/**
 * Created by xander on 10/20/13.
 */
public class EditStaffWindowCommand implements ICommand {
    ServiceProvider provider = null;

    public EditStaffWindowCommand(ServiceProvider provider) {
        this.provider = provider;
    }

    @Override
    public void execute() {
        FormView serviceProviderFormUI = new FormView();
        ServiceProviderController controller = new ServiceProviderController(serviceProviderFormUI, provider);
        serviceProviderFormUI.show();
    }
}
