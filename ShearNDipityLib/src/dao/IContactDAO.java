package dao;


import dao.events.ContactAddedListener;
import dao.events.ContactsUpdatedListener;
import Models.Contact;

public interface IContactDAO {

    public Contact get(int id);

    public Contact get(String name, String surname);

    public void update(Contact c);

    public void remove(Contact c);

    public void remove(int id);

    public void addAddedEventListener(ContactAddedListener listener);

    public void removeAddedEventListener(ContactAddedListener listener);

    public void addUpdatedEventLister(ContactsUpdatedListener listener);

    public void removeUpdatedEventListener(ContactsUpdatedListener listener);

}
