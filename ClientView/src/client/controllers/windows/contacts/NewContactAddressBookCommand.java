package client.controllers.windows.contacts;

import client.controllers.ICommand;
import client.stages.contacts.AddressBook;


public class NewContactAddressBookCommand implements ICommand {

    @Override
    public void execute() {
        new AddressBook().show();
    }
}
