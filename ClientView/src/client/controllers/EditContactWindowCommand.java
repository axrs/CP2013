package client.controllers;

import Controllers.ContactController;
import Models.Contact;
import client.ContactFormView;

public class EditContactWindowCommand implements ICommand {

    Contact contact = null;

    public EditContactWindowCommand(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void execute() {
        ContactFormView contactFormUI = new ContactFormView();
        ContactController controller = new ContactController(contactFormUI, contact);
        contactFormUI.show();
    }
}
