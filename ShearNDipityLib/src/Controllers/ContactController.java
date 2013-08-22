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
 *
 * Singleton contact controller class tasked at interacting with the REST API server and managing
 * any known contacts in memory.
 *
 * Notes:
 * As this class is of a singleton design pattern, Mutexes (or locks) are used throughout any method
 * which requires manipulating of the contact hash map.  If a Mutex is not used, cross thread exceptions
 * will occur.
 *
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

    /**
     * Add a response subscriber
     *
     * @param listener
     */
    public void addListner(ContactsListener listener) {
        subscribers.add(ContactsListener.class, listener);
    }

    /**
     * Remove a response subscriber
     *
     * @param listener
     */
    public void removeListener(ContactsListener listener) {
        subscribers.remove(ContactsListener.class, listener);
    }

    /**
     * Notifies all event listeners of the Contact Controller that there has been a contact update
     *
     * @param event
     */
    private void triggerUpdated(ContactsUpdated event) {

        Object[] listeners = subscribers.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            //Each listener has two components. The listener and the listener instance.
            if (listeners[i] == ContactsListener.class) {
                ((ContactsListener) listeners[i + 1]).updated(event);
            }
        }
    }


    /**
     * Contact Controller Updated Contacts Listener
     */
    public interface ContactsListener extends EventListener {
        public void updated(ContactsUpdated event);
    }

    /**
     * REST Server Results event listener.
     * Implemented specifically to handle processing 'GET all Contacts' requests
     */
    private class GetContactsResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Print the outputs for now
            System.out.println(result.getStatus());
            System.out.println(result.getResponse());

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
     * Contacts Updated Event
     */
    public class ContactsUpdated extends EventObject {
        public ContactsUpdated(Object source) {
            super(source);
        }
    }

}
