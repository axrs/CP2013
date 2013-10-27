package client.controllers.windows.contacts;

import Models.Contact;
import client.controllers.ICommand;
import client.stages.contacts.FormView;

public class EditContactWindowCommand implements ICommand {

    Contact contact = null;

    public EditContactWindowCommand(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void execute() {
        FormView contactFormUI = new FormView(contact);
        contactFormUI.show();
    }
}
