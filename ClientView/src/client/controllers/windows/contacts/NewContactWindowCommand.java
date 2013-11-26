package client.controllers.windows.contacts;

import client.controllers.ICommand;
import client.controllers.windows.core.NewStage;
import client.stages.contacts.FormView;

public class NewContactWindowCommand extends NewStage implements ICommand {

    @Override
    public void execute() {
        new FormView().show();
    }
}
