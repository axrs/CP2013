package client.controllers.windows.appointments;

import client.controllers.ICommand;
import client.stages.appointments.ManageTypes;

public class ShowTypesWindow implements ICommand {
    @Override
    public void execute() {
        new ManageTypes().show();
    }
}
