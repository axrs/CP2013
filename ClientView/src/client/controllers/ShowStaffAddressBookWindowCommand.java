package client.controllers;

import client.StaffAddressBookView;

public class ShowStaffAddressBookWindowCommand extends NewStage implements ICommand {

    @Override
    public void execute() {
        tryStageStart(new StaffAddressBookView());
    }
}
