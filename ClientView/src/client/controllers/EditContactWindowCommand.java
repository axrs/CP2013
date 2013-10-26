package client.controllers;

import Controllers.ContactController;
import Models.Contact;
import client.stages.contacts.FormView;

public class EditContactWindowCommand implements ICommand {

    Contact contact = null;

    public EditContactWindowCommand(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void execute() {
        FormView contactFormUI = new FormView();
        ContactController controller = new ContactController(contactFormUI, contact);
        contactFormUI.show();
    }
}
