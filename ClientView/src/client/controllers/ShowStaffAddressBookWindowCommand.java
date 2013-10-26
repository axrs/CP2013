package client.controllers;

import client.stages.staff.AddressBook;

public class ShowStaffAddressBookWindowCommand extends NewStage implements ICommand {

    @Override
    public void execute() {
        new AddressBook().show();
    }
}
