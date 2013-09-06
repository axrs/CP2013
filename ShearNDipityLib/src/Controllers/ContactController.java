package Controllers;

import Models.Config;
import Models.Contact;
import com.google.gson.Gson;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;

/**
 * Contact Controller
 * <p/>
 * Singleton contact controller class tasked at interacting with the REST API server and managing
 * any known contacts in memory.
 * <p/>
 * Notes:
 * As this class is of a singleton design pattern, Mutexes (or locks) are used throughout any method
 * which requires manipulating of the contact hash map.  If a Mutex is not used, cross thread exceptions
 * will occur.
 * <p/>
 * Created by xander on 8/22/13.
 */
public class ContactController {

    private static ContactController instance = null;
    protected EventListenerList subscribers = new EventListenerList();
    private HashMap<Integer, Contact> contacts;
    private Mutex contactsLocker;
    private Date lastUpdate;


    /**
     * Singleton constructor.  Not able to be overridden
     */
    protected ContactController() {
        if (contactsLocker == null) {
            contactsLocker = new Mutex();
            lastUpdate = new Date();
            contacts = new HashMap<Integer, Contact>();
        }
        if ((new Date().getTime() - lastUpdate.getTime()) > 60000) {
            getContactsFromServer();
        }
    }

    /**
     * Method of obtaining the ContactController instance
     *
     * @return ContactController
     */
    public static ContactController getInstance() {
        //If a static instance of the controller doesn't exist, make it.
        if (instance == null) {
            instance = new ContactController();
        }
        return instance;
    }

    /**
     * Attempts to get a contact with the specified contact id
     *
     * @param id Contact Id
     * @return contact with a specified id or null.
     */
    public Contact getContact(int id) {
        Contact c = null;
        try {
            contactsLocker.acquire();
            try {
                if (contacts.containsKey(id)) {
                    c = contacts.get(id);
                }
            } finally {
                contactsLocker.release();
            }
        } catch (InterruptedException ie) {

        }

        //Request to try and get single id from the server is placed outside the mutex
        //to ensure we have release the mutex prior to the event return
        if (c == null) {
            getContactFromServer(id);
        }
        return c;
    }

    /**
     * Returns the total number of contacts
     *
     * @return contact count
     */
    public int countContacts() {
        int result = 0;
        try {
            contactsLocker.acquire();
            try {
                result = contacts.size();
            } finally {
                contactsLocker.release();
            }
        } catch (InterruptedException ie) {

        }
        return result;
    }

    /**
     * Gets the current contacts map
     *
     * @return
     */
    public HashMap<Integer, Contact> getContacts() {
        HashMap<Integer, Contact> map = null;
        try {
            contactsLocker.acquire();
            try {
                map = contacts;
            } finally {
                contactsLocker.release();
            }
        } catch (InterruptedException ie) {

        }
        return map;
    }

    /**
     * Issues a request to update the contact map from the server
     */
    public void getContactsFromServer() {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetContactsResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/contacts");
        Thread runnerThread = new Thread(runner, "Getting Contacts");
        runnerThread.start();
    }

    public void getContactFromServer(int id) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetContactResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/contacts/" + String.valueOf(id));
        Thread runnerThread = new Thread(runner, "Getting Contact");
        runnerThread.start();
    }

    public void createContact(Contact contact) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyContactResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/contacts");
        runner.setMethod("PUT");
        runner.setMessage(new Gson().toJson(contact, Contact.class));
        Thread runnerThread = new Thread(runner, "Creating Contact");
        runnerThread.start();
    }

    public void updateContact(Contact contact) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyContactResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/contacts/" + String.valueOf(contact.getContId()));
        runner.setMethod("PUT");
        runner.setMessage(new Gson().toJson(contact, Contact.class));
        Thread runnerThread = new Thread(runner, "Updating Contact");
        runnerThread.start();
    }

    /**
     * Add a response subscriber
     *
     * @param listener
     */
    public void addUpdatedListener(ContactsUpdatedListener listener) {
        subscribers.add(ContactsUpdatedListener.class, listener);
    }

    /**
     * Remove a response subscriber
     *
     * @param listener
     */
    public void removeUpdatedListener(ContactsUpdatedListener listener) {
        subscribers.remove(ContactsUpdatedListener.class, listener);
    }

    public void addAddedListener(ContactAddedListener listener) {
        subscribers.add(ContactAddedListener.class, listener);
    }

    public void removeAddedListener(ContactAddedListener listener) {
        subscribers.remove(ContactAddedListener.class, listener);
    }

    /**
     * Notifies all event listeners of the Contact Controller that there has been a contact update
     *
     * @param event
     */
    private void triggerUpdated(ContactsUpdated event) {
        ContactsUpdatedListener[] listeners = subscribers.getListeners(ContactsUpdatedListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].updated(event);
        }
    }

    /**
     * Fires an event to all listeners informing them of a new contact added to the list
     *
     * @param event
     */
    private void triggerAdded(ContactAdded event) {
        ContactAddedListener[] listeners = subscribers.getListeners(ContactAddedListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].added(event);
        }
    }

    /**
     * Contact Controller Updated Contacts Listener
     */
    public interface ContactsUpdatedListener extends EventListener {
        public void updated(ContactsUpdated event);
    }

    public interface ContactAddedListener extends EventListener {
        public void added(ContactAdded event);
    }

    /**
     * REST Server Results event listener.
     * Implemented specifically to handle processing 'GET all Contacts' requests
     */
    private class GetContactsResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

                   //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 200) return;
            //Process results
            try {
                contactsLocker.acquire();
                try {
                    contacts.clear();
                    Contact[] results = new Gson().fromJson(result.getResponse(), Contact[].class);

                    for (int i = 0; i < results.length; i++) {
                        Contact c = results[i];
                        contacts.put(c.getContId(), c);
                    }

                } finally {
                    contactsLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Trigger the ContactController collection updated
            triggerUpdated(new ContactsUpdated(this));
        }
    }

    /**
     * REST Server Results listener.
     * Implemented specifically to handle processing 'GET contact with id' requests.
     */
    private class GetContactResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            Contact c = null;
            if (result.getStatus() != 200) return;
            //Process results
            try {
                contactsLocker.acquire();
                try {
                    c = new Gson().fromJson(result.getResponse(), Contact.class);

                    if (c.getContId() != 0) {
                        contacts.put(c.getContId(), c);
                    }
                } finally {
                    contactsLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Fire events for individual contact added, and all contacts list updated
            if (c != null) {
                triggerAdded(new ContactAdded(this, c));
                triggerUpdated(new ContactsUpdated(this));
            }
        }
    }

    private class ModifyContactResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 201 && result.getStatus() != 202) return;

            //Update Contacts with new information
            getContactsFromServer();
        }
    }

    /**
     * Contacts Updated Event
     */
    public class ContactsUpdated extends EventObject {
        public ContactsUpdated(Object source) {
            super(source);
        }
    }

    /**
     * Contact Added Event
     */
    public class ContactAdded extends EventObject {

        private Contact contact;

        public ContactAdded(Object source, Contact contact) {
            super(source);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }
}
