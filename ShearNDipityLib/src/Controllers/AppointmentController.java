package Controllers;

import Models.Appointment;
import Models.Config;
import Models.Appointment;
import Models.Contact;
import com.google.gson.Gson;

import javax.swing.event.EventListenerList;
import java.util.Date;
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

    public void getAppointmentsFromServer() {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetAppointmentsResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/appointments");
        Thread runnerThread = new Thread(runner, "Getting Appointments");
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
                        appointments.put(a.getContId(), a);
                    }

                } finally {
                    appointmentsLocker.release();
                }
            } catch (InterruptedException ie) {
            }

            //Trigger the ContactController collection updated
          //  triggerUpdated(new ContactsUpdated(this));
        }
    }

}
