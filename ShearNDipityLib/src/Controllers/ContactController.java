package Controllers;

import Models.Contact;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;

/**
 * Created by xander on 8/22/13.
 */
public class ContactController {

    private static ContactController instance = null;
    protected EventListenerList subscribers = new EventListenerList();
    private HashMap<Integer, Contact> contacts;
    private Mutex contactsLocker;
    private Date lastUpdate;


    protected ContactController() {
        if (contactsLocker == null) {
            contactsLocker = new Mutex();
            lastUpdate = new Date();
            contacts = new HashMap<Integer, Contact>();
        }
        if ((new Date().getTime() - lastUpdate.getTime()) > 60000) {

        }
    }

    public static ContactController getInstance() {
        if (instance == null) {
            instance = new ContactController();
        }
        return instance;
    }

    /**
     * Attempts to get a contact with the specified contact id
     *
     * @param id
     * @return
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

    public int countContacts() {
        int result = -1;
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
        runner.setRequest("http://shear-n-dipity.com/api/contacts");

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

            System.out.println(result.getStatus());
            System.out.println(result.getResponse());

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

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
