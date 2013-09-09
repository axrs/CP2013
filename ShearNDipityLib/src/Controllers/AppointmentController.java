package Controllers;

import Models.Appointment;
import Models.Availability;
import Models.Config;
import Models.ScheduledAppointment;
import com.google.gson.Gson;

import javax.swing.event.EventListenerList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 9:41 PM
 * <p/>
 * Appointment Controller
 * <p/>
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
    private Availability[] availabilities = null;
    private ContactsController contactController;
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
    }

    public static AppointmentController getInstance() {
        //If a static instance of the controller doesn't exist, make it.
        if (instance == null) {
            instance = new AppointmentController();
        }
        return instance;
    }

    public void getAppointmentsFromServer(Date dateRangeStart, Date dateRangeEnd) {

        startDate = new SimpleDateFormat("yyyy-MM-dd").format(dateRangeStart);
        endDate = new SimpleDateFormat("yyyy-MM-dd").format(dateRangeEnd);

        getAppointmentsFromServer(startDate, endDate);
        getAvailabilitiesFromServer(startDate, endDate);
    }

    private void getAppointmentsFromServer(String start, String end) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetAppointmentsResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff/appointments/" + start + "/" + end);
        Thread runnerThread = new Thread(runner, "Getting Appointments");
        runnerThread.start();

    }

    public void getAvailabilitiesFromServer(Date dateRangeStart, Date dateRangeEnd) {

        startDate = new SimpleDateFormat("yyyy-MM-dd").format(dateRangeStart);
        endDate = new SimpleDateFormat("yyyy-MM-dd").format(dateRangeEnd);

        getAvailabilitiesFromServer(startDate, endDate);
        getAppointmentsFromServer(startDate, endDate);
    }

    private void getAvailabilitiesFromServer(String start, String end) {

        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetAvailabilitiesResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff/available/" + start + "/" + end);
        Thread runnerThread = new Thread(runner, "Getting Availabilities");
        runnerThread.start();
    }

    public void createAppointment(Appointment appointment) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyAppointmentResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff/appointments");
        runner.setMethod("PUT");
        runner.setMessage(new Gson().toJson(appointment, Appointment.class));
        Thread runnerThread = new Thread(runner, "Creating Appointment");
        runnerThread.start();
    }

    public void updateAppointment(Appointment appointment) {

        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyAppointmentResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff/appointments" + String.valueOf(appointment.getAppId()));
        runner.setMethod("PUT");
        runner.setMessage(new Gson().toJson(appointment, Appointment.class));
        Thread runnerThread = new Thread(runner, "Updating Appointment");
        runnerThread.start();
    }

    public void deleteAppointment(int appID) {

        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyAppointmentResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/staff/appointments/" + appID);
        runner.setMethod("DELETE");
        Thread runnerThread = new Thread(runner, "Deleting Appointment");
        runnerThread.start();
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
     * Fires an event to all listeners informing them of a new appointment added to the list
     *
     * @param event
     */
    private void triggerAdded(AppointmentAdded event) {
        AppointmentAddedListener[] listeners = subscribers.getListeners(AppointmentAddedListener.class);
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].added(event);
        }
    }

    /**
     * Add a response subscriber
     *
     * @param listener
     */
    public void addUpdatedListener(AppointmentsUpdatedListener listener) {
        subscribers.add(AppointmentsUpdatedListener.class, listener);
    }

    public void addUpdatedListener(AvailabilitiesUpdatedListener listener) {
        subscribers.add(AvailabilitiesUpdatedListener.class, listener);
    }

    public void removeUpdatedListener(AvailabilitiesUpdatedListener listener) {
        subscribers.remove(AvailabilitiesUpdatedListener.class, listener);
    }

    /**
     * Remove a response subscriber
     *
     * @param listener
     */
    public void removeUpdatedListener(AppointmentsUpdatedListener listener) {
        subscribers.remove(AppointmentsUpdatedListener.class, listener);
    }

    public HashMap<Integer, Appointment> getAppointments() {
        HashMap<Integer, Appointment> map = null;
        try {
            appointmentsLocker.acquire();
            try {
                map = appointments;
            } finally {
                appointmentsLocker.release();
            }
        } catch (InterruptedException ie) {

        }
        return map;
    }

    public Availability[] getAvailabilities() {
        Availability[] results = null;
        try {
            appointmentsLocker.acquire();
            try {
                results = availabilities;
            } finally {
                appointmentsLocker.release();
            }
        } catch (InterruptedException ie) {

        }
        return results;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    /**
     * Appointment Controller Updated Appointments Listener
     */
    public interface AppointmentsUpdatedListener extends EventListener {
        public void updated(AppointmentsUpdated event);
    }


    public interface AvailabilitiesUpdatedListener extends EventListener {
        public void updated(AvailabilitiesUpdated event);
    }

    public interface AppointmentAddedListener extends EventListener {
        public void added(AppointmentAdded event);
    }

    /**
     * REST Server Results event listener.
     * Implemented specifically to handle processing 'GET all Appointments' requests
     */
    private class GetAppointmentsResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the appointment object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 200) return;
            //Process results
            try {
                appointmentsLocker.acquire();
                try {
                    //We don't clear appointments this time as we only want to update what we receive.
                    ScheduledAppointment[] results = new Gson().fromJson(result.getResponse(), ScheduledAppointment[].class);

                    for (int i = 0; i < results.length; i++) {
                        Appointment a = results[i];
                        appointments.put(a.getAppId(), a);
                    }
                } finally {
                    appointmentsLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Trigger the AppointmentController collection updated
            triggerUpdated(new AppointmentsUpdated(this));
        }
    }

    /**
     * REST Server Results event listener.
     * Implemented specifically to handle processing 'GET all Availabilities' requests
     */
    private class GetAvailabilitiesResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the appointment object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 200) return;
            //Process results
            try {
                appointmentsLocker.acquire();
                try {

                    availabilities = new Gson().fromJson(result.getResponse(), Availability[].class);
                } finally {
                    appointmentsLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Trigger the AppointmentController collection updated
            triggerUpdated(new AvailabilitiesUpdated(this));
        }
    }

    /**
     * REST Server Results listener.
     * Implemented specifically to handle processing 'GET Appointment with id' requests.
     */
    private class GetAppointmentResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the appointment object
            ((RESTRunner) result.getSource()).removeListener(this);

            Appointment a = null;
            if (result.getStatus() != 200) return;
            //Process results
            try {
                appointmentsLocker.acquire();
                try {
                    a = new Gson().fromJson(result.getResponse(), Appointment.class);

                    if (a.getContId() != 0) {
                        appointments.put(a.getAppId(), a);
                    }
                } finally {
                    appointmentsLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Fire events for individual appointment added, and all appointments list updated
            if (a != null) {
                triggerAdded(new AppointmentAdded(this, a));
                triggerUpdated(new AppointmentsUpdated(this));
            }
        }
    }

    private class ModifyAppointmentResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {
            //Remove the listener from the appointment object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 201 && result.getStatus() != 202) return;

            //Update Appointments with new information
            getAppointmentsFromServer(AppointmentController.getInstance().getStartDate(), AppointmentController.getInstance().getEndDate());
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
     * Availabilities Updated Event
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

        public Appointment getAppointment(Date dateRangeStart, Date dateRangeEnd) {
            return appointment;
        }
    }
}