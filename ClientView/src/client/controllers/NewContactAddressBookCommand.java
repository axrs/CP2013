package client.controllers;

import client.ContactAddressBookView;


public class NewContactAddressBookCommand extends NewStage implements ICommand {

    @Override
    public void execute() {

        tryStageStart(new ContactAddressBookView());

    }
}
