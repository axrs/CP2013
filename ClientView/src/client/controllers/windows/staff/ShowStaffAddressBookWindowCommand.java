package client.controllers.windows.staff;

import client.controllers.ICommand;
import client.controllers.windows.core.NewStage;
import client.stages.staff.AddressBook;

public class ShowStaffAddressBookWindowCommand extends NewStage implements ICommand {

    @Override
    public void execute() {
        new AddressBook().show();
    }
}
