package dao;


import models.Contact;
import dao.events.ContactAddedListener;
import dao.events.ContactsUpdatedListener;
import dao.restDAO.listeners.ResultListener;

public interface IContactDAO {

    public Contact[] getStore();

    public Contact get(int id);

    public void create(Contact contact, ResultListener listener);

    public void create(Contact contact);

    public void update(Contact contact, ResultListener listener);

    public void update(Contact contact);

    public void remove(Contact contact, ResultListener listener);

    public void remove(Contact contact);

    public void remove(int id, ResultListener listener);

    public void remove(int id);

    public void addAddedEventListener(ContactAddedListener listener);

    public void removeAddedEventListener(ContactAddedListener listener);

    public void addUpdatedEventLister(ContactsUpdatedListener listener);

    public void removeUpdatedEventListener(ContactsUpdatedListener listener);

}
