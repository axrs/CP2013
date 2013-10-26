package dao.rest.stores;

import Models.Contact;
import dao.DataStore;

public class ContactDataStore extends DataStore {
    @Override
    public Contact get(int id) {
        return (Contact) super.get(id);
    }

    public Contact[] getValues() {
        return (Contact[]) super.getValues();
    }

    public void update(int id, Contact contact) {
        super.update(id, contact);
    }

    public void add(int id, Contact contact) {
        super.add(id, contact);
    }
}
