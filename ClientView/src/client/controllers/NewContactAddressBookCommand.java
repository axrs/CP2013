package client.controllers;

import client.stages.contacts.AddressBook;


public class NewContactAddressBookCommand implements ICommand {

    @Override
    public void execute() {
        new AddressBook().show();
    }
}
