package Controllers;

import Models.AppointmentType;
import Models.Config;
import Models.Contact;
import com.google.gson.Gson;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 29/08/13
 * Time: 12:09 PM
 *
 * Appointment Type Controller
 *
 * Singleton Appointment Type controller class tasked at interacting with the REST API server and managing
 * any known Appointment Type in memory.
 *
 * Notes:
 * As this class is of a singleton design pattern, Mutexes (or locks) are used throughout any method
 * which requires manipulating of the contact hash map.  If a Mutex is not used, cross thread exceptions
 * will occur.

 */
public class AppointmentTypeController {

    private static AppointmentTypeController instance = null;
    protected EventListenerList subscribers = new EventListenerList();
    private HashMap<Integer, AppointmentType> appointmentTypes;
    private Mutex appointmentTypesLocker;
    private Date lastUpdate;

    /**
     * Singleton constructor.  Not able to be overridden
     */
    protected AppointmentTypeController() {
        if (appointmentTypesLocker == null) {
            appointmentTypesLocker = new Mutex();
            lastUpdate = new Date();
            appointmentTypes = new HashMap<Integer, AppointmentType>();
        }
        if ((new Date().getTime() - lastUpdate.getTime()) > 60000) {
            getAppointmentTypesFromServer();
        }
    }

    public HashMap<Integer, AppointmentType> getAppointmentTypes() {
        HashMap<Integer, AppointmentType> map = null;
        try {
            appointmentTypesLocker.acquire();
            try {
                map = appointmentTypes;
            } finally {
                appointmentTypesLocker.release();
            }
        } catch (InterruptedException ie) {

        }
        return map;
    }


    /**
     * Method of obtaining the AppointmentTypeController instance
     *
     * @return AppointmentTypeController
     */
    public static AppointmentTypeController getInstance() {
        //If a static instance of the controller doesn't exist, make it.
        if (instance == null) {
            instance = new AppointmentTypeController();
            instance.getAppointmentTypesFromServer();
        }
        return instance;
    }

    /**
     * Attempts to get an AppointmentType with the specified appointment type id
     *
     * @param id appointmentType Id
     * @return appointmentType with a specified id or null.
     */
    public AppointmentType getAppointmentType(int id) {
        AppointmentType appType = null;
        try {
            appointmentTypesLocker.acquire();
            try {
                if (appointmentTypes.containsKey(id)) {
                    appType = appointmentTypes.get(id);
                }
            } finally {
                appointmentTypesLocker.release();
            }
        } catch (InterruptedException ie) {

        }

        return appType;
    }

    public void getAppointmentTypesFromServer() {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetAppointmentTypesResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/appointments/types");
        Thread runnerThread = new Thread(runner, "Getting Appointment Types");
        runnerThread.start();
    }

    /**
     * REST Server Results event listener.
     * Implemented specifically to handle processing 'GET all Appointment Types' requests
     */
    private class GetAppointmentTypesResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Print the outputs for now
            System.out.println("Get All Appointment Types Request : " + result.getStatus());
            System.out.println(result.getResponse());

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 200) return;
            //Process results
            try {
                appointmentTypesLocker.acquire();
                try {
                    appointmentTypes.clear();
                    AppointmentType[] results = new Gson().fromJson(result.getResponse(), AppointmentType[].class);

                    for (int i = 0; i < results.length; i++) {
                        AppointmentType appType = results[i];
                        appointmentTypes.put(appType.getAppTypeId(), appType);
                    }

                } finally {
                    appointmentTypesLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Trigger the ContactController collection updated
            triggerUpdated(new AppointmentTypesUpdated(this));
        }
    }

    /**
     * Notifies all event listeners of the Appointment Type Controller that there has been a Appointment Type update
     *
     * @param event
     */
    private void triggerUpdated(AppointmentTypesUpdated event) {
        AppointmentTypesUpdatedListener[] listeners = subscribers.getListeners(AppointmentTypesUpdatedListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].updated(event);
        }
    }

    /**
     * Appointment Type Controller Updated Appointment Types Listener
     */
    public interface AppointmentTypesUpdatedListener extends EventListener {
        public void updated(AppointmentTypesUpdated event);
    }

    /**
     * Appointment Types Updated Event
     */
    public class AppointmentTypesUpdated extends EventObject {
        public AppointmentTypesUpdated(Object source) {
            super(source);
        }
    }

}
