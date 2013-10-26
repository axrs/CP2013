package dao.events;

import Models.Contact;

import java.util.EventObject;

public class ContactAddedEvent extends EventObject {

    private Contact contact;

    public ContactAddedEvent(Object source, Contact contact) {
        super(source);
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }
}
