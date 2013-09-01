package Controllers;

import Models.*;
import Models.Appointment;
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
 * Time: 9:41 PM
 *
 *  Appointment Controller
 *
 * Singleton Appointment controller class tasked at interacting with the REST API server and managing
 * any known Appointment in memory.
 * Notes:
 * As this class is of a singleton design pattern, Mutexes (or locks) are used throughout any method
 * which requires manipulating of the ServiceProvider hash map.  If a Mutex is not used, cross thread exceptions
 * will occur.
 */

public class AppointmentController {

    private static AppointmentController instance = null;
    protected EventListenerList subscribers = new EventListenerList();
    private HashMap<Integer, Appointment> appointments;
    private Mutex appointmentsLocker;
    private Date lastUpdate;
    private String startDate;
    private String endDate;
    private Availability[] availabilities;
    private ContactController contactController;
    private ServiceProviderController serviceProviderController;

    /**
     * Singleton constructor.  Not able to be overridden
     */
    protected AppointmentController() {
        if (appointmentsLocker == null) {
            appointmentsLocker = new Mutex();
            lastUpdate = new Date();
            appointments = new HashMap<Integer, Appointment>();
        }
        if ((new Date().getTime() - lastUpdate.getTime()) > 60000) {
            getAppointmentsFromServer();
        }
    }

    public static AppointmentController getInstance() {
        //If a static instance of the controller doesn't exist, make it.
        if (instance == null) {
            instance = new AppointmentController();
            instance.getAppointmentsFromServer();
        }
        return instance;
    }

    public void getAppointmentsFromServer() {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetAppointmentsResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/appointments");
        Thread runnerThread = new Thread(runner, "Getting Appointments");
        runnerThread.start();
    }

    public void getAvailabilitiesFromServer() {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetAvailabilitiesResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff/available/" + startDate + "/" + endDate);
        Thread runnerThread = new Thread(runner, "Getting Availabilities");
        runnerThread.start();
    }

    public void createAppointment(Appointment appointment) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyAppointmentResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/appointments");
        runner.setMethod("PUT");
        runner.setMessage(new Gson().toJson(appointment, Appointment.class));
        Thread runnerThread = new Thread(runner, "Creating Appointment");
        runnerThread.start();
    }

    public void updateAppointment(Appointment appointment) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyAppointmentResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/contacts/" + String.valueOf(appointment.getAppId()));
        runner.setMethod("PUT");
        runner.setMessage(new Gson().toJson(appointment, Appointment.class));
        Thread runnerThread = new Thread(runner, "Updating Appointment");
        runnerThread.start();
    }


    /**
     * REST Server Results event listener.
     * Implemented specifically to handle processing 'GET all Contacts' requests
     */
    private class GetAppointmentsResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Print the outputs for now
            System.out.println("Get All Appointments Request : " + result.getStatus());
            System.out.println(result.getResponse());

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 200) return;
            //Process results
            try {
                appointmentsLocker.acquire();
                try {
                    appointments.clear();
                    Appointment[] results = new Gson().fromJson(result.getResponse(), Appointment[].class);

                    for (int i = 0; i < results.length; i++) {
                        Appointment a = results[i];
                        appointments.put(a.getAppId(), a);
                    }

                } finally {
                    appointmentsLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Trigger the ContactController collection updated
            triggerUpdated(new AppointmentsUpdated(this));
        }
    }

    /**
     * REST Server Results event listener.
     * Implemented specifically to handle processing 'GET all Contacts' requests
     */
    private class GetAvailabilitiesResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Print the outputs for now
            System.out.println("Get All Availabilities Request : " + result.getStatus());
            System.out.println(result.getResponse());

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 200) return;
            //Process results
            try {
                appointmentsLocker.acquire();
                try {
                    Availability[] results = new Gson().fromJson(result.getResponse(), Availability[].class);

                    for (int i = 0; i < results.length; i++) {
                        Availability a = results[i];
                        availabilities[i] = a;
                    }

                } finally {
                    appointmentsLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Trigger the ContactController collection updated
              triggerUpdated(new AvailabilitiesUpdated(this));
        }
    }

    private class ModifyAppointmentResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {
            //Print the outputs for now
            System.out.println("Modify single Appointment : " + result.getStatus());
            System.out.println(result.getResponse());

            //Remove the listener from the appointment object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 201 && result.getStatus() != 202) return;

            //Update Appointments with new information
            getAppointmentsFromServer();
        }
    }

    /**
     * Appointments Updated Event
     */
    public class AppointmentsUpdated extends EventObject {
        public AppointmentsUpdated(Object source) {
            super(source);
        }
    }

    /**
     * Availablities Updated Event
     */
    public class AvailabilitiesUpdated extends EventObject {
        public AvailabilitiesUpdated(Object source) {
            super(source);
        }
    }
    /**
     * Appointment Added Event
     */
    public class AppointmentAdded extends EventObject {

        private Appointment appointment;

        public AppointmentAdded(Object source, Appointment appointment) {
            super(source);
            this.appointment = appointment;
        }

        public Appointment getAppointment() {
            return appointment;
        }
    }

    /**
     * Notifies all event listeners of the Appointments Controller that there has been a Appointments update
     *
     * @param event
     */
    private void triggerUpdated(AppointmentsUpdated event) {
        AppointmentsUpdatedListener[] listeners = subscribers.getListeners(AppointmentsUpdatedListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].updated(event);
        }
    }

    /**
     * Notifies all event listeners of the Appointments Controller that there has been a Appointments update
     *
     * @param event
     */
    private void triggerUpdated(AvailabilitiesUpdated event) {
        AvailabilitiesUpdatedListener[] listeners = subscribers.getListeners(AvailabilitiesUpdatedListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].updated(event);
        }
    }


    /**
     * Contact Controller Updated Contacts Listener
     */
    public interface AppointmentsUpdatedListener extends EventListener {
        public void updated(AppointmentsUpdated event);
    }

    public interface AvailabilitiesUpdatedListener extends EventListener {
        public void updated(AvailabilitiesUpdated event);
    }

}