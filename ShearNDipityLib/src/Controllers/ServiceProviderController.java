package Controllers;

import Models.Contact;
import Models.ServiceProvider;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 6:29 PM
 *
 * ServiceProvider Controller
 *
 * Singleton ServiceProvider controller class tasked at interacting with the REST API server and managing
 * any known ServiceProvider in memory.
 * Notes:
 * As this class is of a singleton design pattern, Mutexes (or locks) are used throughout any method
 * which requires manipulating of the ServiceProvider hash map.  If a Mutex is not used, cross thread exceptions
 * will occur.
 */

public class ServiceProviderController {

    private static ServiceProviderController instance = null;
    protected EventListenerList subscribers = new EventListenerList();
    private HashMap<Integer, ServiceProvider> serviceProvider;
    private Mutex serviceProviderLocker;
    private Date lastUpdate;


    /**
     * Singleton constructor.  Not able to be overridden
     */
    protected ServiceProviderController() {
        if (serviceProviderLocker == null) {
            serviceProviderLocker = new Mutex();
            lastUpdate = new Date();
            serviceProviders = new HashMap<Integer, ServiceProvider>();
        }
        if ((new Date().getTime() - lastUpdate.getTime()) > 60000) {
            getServiceProvidersFromServer();
        }
    }

    /**
     * Method of obtaining the ServiceProviderController instance
     *
     * @return ServiceProviderController
     */
    public static ServiceProviderController getInstance() {
        //If a static instance of the controller doesn't exist, make it.
        if (instance == null) {
            instance = new ServiceProviderController();
        }
        return instance;
    }
    /**
     * Attempts to get a service provider with the specified service provider id
     *
     * @param id ServiceProvider Id
     * @return service provider with a specified id or null.
     */
    public ServiceProvider getServiceProvider(int id) {
        ServiceProvider sp = null;
        try {
            serviceProviderLocker.acquire();
            try {
                if (serviceProvider.containsKey(id)) {
                    sp = serviceProvider.get(id);
                }
            } finally {
                serviceProviderLocker.release();
            }
        } catch (InterruptedException ie) {

        }

        //Request to try and get single id from the server is placed outside the mutex
        //to ensure we have release the mutex prior to the event return
        if (sp == null) {
            getServiceProviderFromServer(id);
        }
        return sp;
    }
    /**
     * Returns the total number of service provider
     *
     * @return service provider count
     */
    public int countServiceProviders() {
        int result = 0;
        try {
            serviceProviderLocker.acquire();
            try {
                result = serviceProvider.size();
            } finally {
                serviceProviderLocker.release();
            }
        } catch (InterruptedException ie) {

        }
        return result;
    }

    /**
     * Gets the current service provider map
     *
     * @return
     */
    public HashMap<Integer, ServiceProvider> getServiceProvider() {
        HashMap<Integer, ServiceProvider> map = null;
        try {
            serviceProviderLocker.acquire();
            try {
                map = serviceProvider;
            } finally {
                serviceProviderLocker.release();
            }
        } catch (InterruptedException ie) {

        }
        return map;
    }



}
