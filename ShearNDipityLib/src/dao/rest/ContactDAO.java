package dao.rest;

import Models.Contact;
import com.google.gson.Gson;
import dao.IContactDAO;
import dao.events.ContactAddedEvent;
import dao.events.ContactAddedListener;
import dao.events.ContactsUpdatedListener;
import dao.events.UpdatedEvent;
import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;
import dao.rest.requests.Request;
import dao.rest.requests.contacts.GetAllContactsRequest;

import java.util.HashMap;

public class ContactDAO extends Publisher implements IContactDAO {
    private static ContactDAO instance = null;
    private HashMap<Integer, Contact> contacts;


    protected ContactDAO() {
        Request r = new GetAllContactsRequest();
        r.addResultListener(onGetAllContactsResult());
        ActiveRESTClient.addRequest(new GetAllContactsRequest());

    }

    public static ContactDAO getInstance() {
        if (instance == null) {
            instance = new ContactDAO();
        }
        return instance;
    }

    private ResultListener onGetAllContactsResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 200) return;
                //Process results
                writeLock.lock();
                try {
                    contacts.clear();

                    Contact[] results = new Gson().fromJson(result.getResponse(), Contact[].class);

                    for (Contact c : results) {
                        contacts.put(c.getContactId(), c);
                    }
                } finally {
                    writeLock.unlock();
                }

                //Trigger the ContactsController collection updated
                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    @Override
    public void addAddedEventListener(ContactAddedListener listener) {
        subscribers.add(ContactAddedListener.class, listener);
    }

    @Override
    public void removeAddedEventListener(ContactAddedListener listener) {
        subscribers.remove(ContactAddedListener.class, listener);
    }

    @Override
    public void addUpdatedEventLister(ContactsUpdatedListener listener) {
        subscribers.add(ContactsUpdatedListener.class, listener);
    }

    @Override
    public void removeUpdatedEventListener(ContactsUpdatedListener listener) {
        subscribers.remove(ContactsUpdatedListener.class, listener);
    }

    @Override
    public Contact get(int id) {
        Contact c = null;

        readLock.lock();
        try {
            if (contacts.containsKey(id)) {
                c = contacts.get(id);
            }
        } finally {
            readLock.unlock();
        }
        return c;
    }

    @Override
    public Contact get(String name, String surname) {
        Contact contact = null;

        readLock.lock();
        try {
            for (int key : contacts.keySet()) {
                Contact c = contacts.get(key);
                if (c.getName().equals(name) && c.getSurname().equals(surname)) {
                    contact = c;
                    break;
                }
            }
        } finally {
            readLock.unlock();
        }
        return contact;
    }

    private void fireUpdated(UpdatedEvent event) {
        ContactsUpdatedListener[] listeners = subscribers.getListeners(ContactsUpdatedListener.class);
        for (ContactsUpdatedListener listener : listeners) {
            listener.updated(event);
        }
    }

    private void fireAdded(ContactAddedEvent event) {
        ContactAddedListener[] listeners = subscribers.getListeners(ContactAddedListener.class);
        for (ContactAddedListener listener : listeners) {
            listener.added(event);
        }
    }

    @Override
    public void update(Contact c) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(Contact c) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(int id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
