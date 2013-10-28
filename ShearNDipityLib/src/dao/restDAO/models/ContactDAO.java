package dao.restDAO.models;

import Models.Contact;
import com.google.gson.Gson;
import dao.IContactDAO;
import dao.events.ContactAddedEvent;
import dao.events.ContactAddedListener;
import dao.events.ContactsUpdatedListener;
import dao.events.UpdatedEvent;
import dao.restDAO.client.ActiveRESTClient;
import dao.restDAO.Publisher;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import dao.restDAO.requests.Request;
import dao.restDAO.requests.contacts.*;
import dao.restDAO.stores.ContactDataStore;

public class ContactDAO extends Publisher implements IContactDAO {

    /**
     * This is a Locker base data store.  It implements the synchronised methods required.
     */
    private static ContactDataStore store = null;
    /**
     * The Single ContactDAO Class in existence
     */
    private static ContactDAO instance = null;

    /**
     * Private Constructor
     */
    protected ContactDAO() {
        Request r = new GetAllContactsRequest();
        r.addResultListener(onGetAllContactsResult());
        ActiveRESTClient.addRequest(r);
    }

    /**
     * Get class instance
     *
     * @return ContactDAO Instance
     */
    public static ContactDAO getInstance() {
        if (instance == null) {
            instance = new ContactDAO();
            store = new ContactDataStore();
        }
        return instance;
    }

    public static Contact[] getMap() {
        return getInstance().getStore();
    }

    @Override
    public Contact[] getStore() {
        return store.getValues();
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
        Contact contact = store.get(id);

        if (contact == null) {
            Request r = new GetContactRequest(id);
            r.addResultListener(onGetContactResult());
            ActiveRESTClient.addRequest(r);
        }

        return contact;
    }

    @Override
    public void create(Contact contact) {
        create(contact, null);
    }

    public void create(Contact contact, ResultListener listener) {
        Request r = new CreateContactRequest(contact);
        if (listener != null) {
            r.addResultListener(listener);
        }
        r.addResultListener(onContactUpdatedResult());
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void update(Contact contact) {
        update(contact, null);
    }

    @Override
    public void update(Contact contact, ResultListener listener) {
        Request r = new UpdateContactRequest(contact);
        r.addResultListener(onContactUpdatedResult());
        if (listener != null) {
            r.addResultListener(listener);
        }
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void remove(int id, ResultListener listener) {
        Request r = new RemoveContactRequest(id);
        r.addResultListener(onContactRemoved(id));
        if (listener != null) {
            r.addResultListener(listener);
        }
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void remove(Contact contact, ResultListener listener) {
        remove(contact.getContactId(), listener);
    }

    @Override
    public void remove(Contact contact) {
        remove(contact.getContactId(), null);
    }

    @Override
    public void remove(int id) {
        remove(id, null);
    }

    /**
     * Method used to notify any subscribers of a change to the data store
     *
     * @param event Event Object
     */
    private void fireUpdated(UpdatedEvent event) {
        ContactsUpdatedListener[] listeners = subscribers.getListeners(ContactsUpdatedListener.class);
        for (ContactsUpdatedListener listener : listeners) {
            listener.updated(event);
        }
    }

    /**
     * Method used to notify any subscribers of a single contact added to the data store
     *
     * @param event Event Object
     */
    private void fireAdded(ContactAddedEvent event) {
        ContactAddedListener[] listeners = subscribers.getListeners(ContactAddedListener.class);
        for (ContactAddedListener listener : listeners) {
            listener.added(event);
        }
    }

    /**
     * Used to process resoluts from a UpdateContactRequest or CreateContactRequest
     *
     * @return Event Listener
     */
    private ResultListener onContactUpdatedResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 201 && result.getStatus() != 202) return;

                Request r = new GetAllContactsRequest();
                r.addResultListener(onGetAllContactsResult());
                ActiveRESTClient.addRequest(r);
            }
        };
    }

    /**
     * Used to process results from a GetContactRequest
     *
     * @return Event Listener
     */
    private ResultListener onGetContactResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                Contact c;
                if (result.getStatus() != 200) return;

                c = new Gson().fromJson(result.getResponse(), Contact.class);
                if (c.getContactId() != 0) {
                    store.add(c.getContactId(), c);
                }

                //Fire events for individual contact added, and all contacts list updated
                if (c != null) {
                    fireAdded(new ContactAddedEvent(this, c));
                    fireUpdated(new UpdatedEvent(this));
                }
            }
        };
    }

    /**
     * Used to process results from a GetAllContactsRequest
     *
     * @return Event Listener
     */
    private ResultListener onGetAllContactsResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 200) return;
                store.clear();
                Contact[] results = new Gson().fromJson(result.getResponse(), Contact[].class);
                for (Contact c : results) {
                    store.add(c.getContactId(), c);
                }
                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    /**
     * Used to process results from a RemoveContactRequest
     *
     * @param id ContactId removed
     * @return Event Listener
     */
    private ResultListener onContactRemoved(final int id) {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 202) return;
                store.remove(id);
                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

}
