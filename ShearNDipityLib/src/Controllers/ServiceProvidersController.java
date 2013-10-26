package Controllers;

import Models.Config;
import Models.ServiceProvider;
import com.google.gson.Gson;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 6:29 PM
 * <p/>
 * ServiceProvider Controller
 * <p/>
 * Singleton ServiceProvider controller class tasked at interacting with the REST API server and managing
 * any known ServiceProvider in memory.
 * Notes:
 * As this class is of a singleton design pattern, Mutexes (or locks) are used throughout any method
 * which requires manipulating of the ServiceProvider hash map.  If a Mutex is not used, cross thread exceptions
 * will occur.
 */

public class ServiceProvidersController {

    private static ServiceProvidersController instance = null;
    protected EventListenerList subscribers = new EventListenerList();
    private HashMap<Integer, ServiceProvider> serviceProviders;
    private Mutex serviceProviderLocker;
    private Date lastUpdate;


    /**
     * Singleton constructor.  Not able to be overridden
     */
    protected ServiceProvidersController() {
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
     * Method of obtaining the ServiceProvidersController instance
     *
     * @return ServiceProvidersController
     */
    public static ServiceProvidersController getInstance() {
        //If a static instance of the controller doesn't exist, make it.
        if (instance == null) {
            instance = new ServiceProvidersController();
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
                if (serviceProviders.containsKey(id)) {
                    sp = serviceProviders.get(id);
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
                result = serviceProviders.size();
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
    public HashMap<Integer, ServiceProvider> getServiceProviders() {
        HashMap<Integer, ServiceProvider> map = null;
        try {
            serviceProviderLocker.acquire();
            try {
                map = serviceProviders;
            } finally {
                serviceProviderLocker.release();
            }
        } catch (InterruptedException ie) {

        }
        return map;
    }

    /**
     * Issues a request to update the contact map from the server
     */
    public void getServiceProvidersFromServer() {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetServiceProvidersResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff");
        Thread runnerThread = new Thread(runner, "Getting Service Providers");
        runnerThread.start();
    }

    public void getServiceProviderFromServer(int id) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetServiceProviderResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff/" + String.valueOf(id));
        Thread runnerThread = new Thread(runner, "Getting Service Provider");
        runnerThread.start();
    }

    public void createServiceProvider(ServiceProvider serviceProvider) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyServiceProviderResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff");
        runner.setMethod("PUT");
        runner.setMessage(new Gson().toJson(serviceProvider, ServiceProvider.class));
        Thread runnerThread = new Thread(runner, "Creating Service Provider");
        runnerThread.start();
    }

    public void updateServiceProvider(ServiceProvider serviceProvider) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyServiceProviderResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff/" + String.valueOf(serviceProvider.getServId()));
        runner.setMethod("PUT");
        runner.setMessage(new Gson().toJson(serviceProvider, ServiceProvider.class));
        Thread runnerThread = new Thread(runner, "Updating Service Provider");
        runnerThread.start();
    }

    public void addUpdatedListener(ServiceProvidersUpdatedListener listener) {
        subscribers.add(ServiceProvidersUpdatedListener.class, listener);
    }

    public void removeUpdatedListener(ServiceProvidersUpdatedListener listener) {
        subscribers.remove(ServiceProvidersUpdatedListener.class, listener);
    }

    public void addAddedListener(ServiceProvidersAddedListener listener) {
        subscribers.add(ServiceProvidersAddedListener.class, listener);
    }

    public void removeAddedListener(ServiceProvidersAddedListener listener) {
        subscribers.remove(ServiceProvidersAddedListener.class, listener);
    }

    /**
     * Notifies all event listeners of the Service Provider Controller that there has been a service provider update
     *
     * @param event
     */
    private void triggerUpdated(ServiceProvidersUpdated event) {
        ServiceProvidersUpdatedListener[] listeners = subscribers.getListeners(ServiceProvidersUpdatedListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].updated(event);
        }
    }

    /**
     * Fires an event to all listeners informing them of a new service provider added to the list
     *
     * @param event
     */
    private void triggerAdded(ServiceProviderAdded event) {
        ServiceProvidersAddedListener[] listeners = subscribers.getListeners(ServiceProvidersAddedListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].added(event);
        }
    }

    /**
     * Contact Controller Updated Contacts Listener
     */
    public interface ServiceProvidersUpdatedListener extends EventListener {
        public void updated(ServiceProvidersUpdated event);
    }

    public interface ServiceProvidersAddedListener extends EventListener {
        public void added(ServiceProviderAdded event);
    }

    /**
     * REST Server Results event listener.
     * Implemented specifically to handle processing 'GET all Service Provider' requests
     */
    private class GetServiceProvidersResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 200) return;
            //Process results
            try {
                serviceProviderLocker.acquire();
                try {
                    serviceProviders.clear();
                    ServiceProvider[] results = new Gson().fromJson(result.getResponse(), ServiceProvider[].class);

                    for (int i = 0; i < results.length; i++) {
                        ServiceProvider sp = results[i];
                        serviceProviders.put(sp.getServId(), sp);
                    }

                } finally {
                    serviceProviderLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Trigger the ServiceProvidersController collection updated
            triggerUpdated(new ServiceProvidersUpdated(this));
        }
    }

    /**
     * REST Server Results listener.
     * Implemented specifically to handle processing 'GET ServiceProvider with id' requests.
     */
    private class GetServiceProviderResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            ServiceProvider sp = null;
            if (result.getStatus() != 200) return;
            //Process results
            try {
                serviceProviderLocker.acquire();
                try {
                    sp = new Gson().fromJson(result.getResponse(), ServiceProvider.class);

                    if (sp.getServId() != 0) {
                        serviceProviders.put(sp.getContactId(), sp);
                    }
                } finally {
                    serviceProviderLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Fire events for individual contact added, and all contacts list updated
            if (sp != null) {
                triggerAdded(new ServiceProviderAdded(this, sp));
                triggerUpdated(new ServiceProvidersUpdated(this));
            }
        }
    }

    private class ModifyServiceProviderResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 201 && result.getStatus() != 202) return;

            //Update Contacts with new information
            getServiceProvidersFromServer();
        }
    }

    /**
     * ServiceProviders Updated Event
     */
    public class ServiceProvidersUpdated extends EventObject {
        public ServiceProvidersUpdated(Object source) {
            super(source);
        }
    }

    /**
     * ServiceProvider Added Event
     */
    public class ServiceProviderAdded extends EventObject {

        private ServiceProvider serviceProvider;

        public ServiceProviderAdded(Object source, ServiceProvider serviceProvider) {
            super(source);
            this.serviceProvider = serviceProvider;
        }

        public ServiceProvider getServiceProvider() {
            return serviceProvider;
        }
    }
}
