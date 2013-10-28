package dao.restDAO.modules;

import com.google.gson.Gson;
import dao.IAppointmentDAO;
import dao.events.AppointmentAddedEvent;
import dao.events.AppointmentsAddedListener;
import dao.events.AppointmentsUpdatedListener;
import dao.events.UpdatedEvent;
import dao.restDAO.Publisher;
import dao.restDAO.client.ActiveRESTClient;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import dao.restDAO.requests.Request;
import dao.restDAO.requests.appointments.CreateAppointmentRequest;
import dao.restDAO.requests.appointments.GetAllAppointmentsRequest;
import dao.restDAO.requests.appointments.RemoveAppointmentRequest;
import dao.restDAO.requests.appointments.UpdateAppointmentRequest;
import dao.restDAO.requests.contacts.GetContactRequest;
import dao.restDAO.stores.AppointmentStore;
import models.Appointment;

public class AppointmentDAO extends Publisher implements IAppointmentDAO {
    private static AppointmentDAO instance = null;
    private static AppointmentStore store = null;

    protected AppointmentDAO() {
        Request r = new GetAllAppointmentsRequest();
        r.addResultListener(onGetAllAppointments());
        ActiveRESTClient.addRequest(r);
    }

    public static AppointmentDAO getInstance() {
        if (instance == null) {
            instance = new AppointmentDAO();
            store = new AppointmentStore();
        }
        return instance;
    }

    public static Appointment[] getMap() {
        return getInstance().getStore();
    }

    @Override
    public Appointment[] getStore() {
        return store.getValues();
    }

    @Override
    public Appointment get(int id) {
        Appointment appointment = store.get(id);

        if (appointment == null) {
            Request r = new GetContactRequest(id);
            r.addResultListener(onAppointmentUpdated());
            ActiveRESTClient.addRequest(r);
        }

        return appointment;
    }

    @Override
    public void create(Appointment appointment, ResultListener listener) {
        Request r = new CreateAppointmentRequest(appointment);
        if (listener != null) {
            r.addResultListener(listener);
        }
        r.addResultListener(onAppointmentUpdated());
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void create(Appointment appointment) {
        create(appointment, null);
    }

    @Override
    public void update(Appointment appointment, ResultListener listener) {
        Request r = new UpdateAppointmentRequest(appointment);
        r.addResultListener(onAppointmentUpdated());
        if (listener != null) {
            r.addResultListener(listener);
        }
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void update(Appointment appointment) {
        update(appointment, null);
    }

    private ResultListener onGetAllAppointments() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 200) return;
                store.clear();
                Appointment[] results = new Gson().fromJson(result.getResponse(), Appointment[].class);
                for (Appointment appointment : results) {
                    store.add(appointment.getAppointmentId(), appointment);
                }
                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    private ResultListener onAppointmentUpdated() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 201 && result.getStatus() != 202) return;
                Request r = new GetAllAppointmentsRequest();
                r.addResultListener(onGetAllAppointments());
                ActiveRESTClient.addRequest(r);
            }
        };
    }

    @Override
    public void remove(Appointment appointment, ResultListener listener) {
        remove(appointment.getAppointmentId(), listener);
    }

    @Override
    public void remove(Appointment appointment) {
        remove(appointment.getAppointmentId());

    }

    @Override
    public void remove(int id, ResultListener listener) {
        Request r = new RemoveAppointmentRequest(id);
        r.addResultListener(onAppointmentRemoved(id));
        if (listener != null) {
            r.addResultListener(listener);
        }
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void remove(int id) {
        remove(id, null);
    }

    /**
     * Used to process results from a RemoveContactRequest
     *
     * @param id ContactId removed
     * @return Event Listener
     */
    private ResultListener onAppointmentRemoved(final int id) {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 202) return;
                store.remove(id);
                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    private void fireUpdated(UpdatedEvent event) {
        AppointmentsUpdatedListener[] listeners = subscribers.getListeners(AppointmentsUpdatedListener.class);
        for (AppointmentsUpdatedListener listener : listeners) {
            listener.updated(event);
        }
    }

    private void fireAdded(AppointmentAddedEvent event) {
        AppointmentsAddedListener[] listeners = subscribers.getListeners(AppointmentsAddedListener.class);
        for (AppointmentsAddedListener listener : listeners) {
            listener.added(event);
        }
    }

    @Override
    public void addAddedEventListener(AppointmentsAddedListener listener) {
        subscribers.add(AppointmentsAddedListener.class, listener);
    }

    @Override
    public void removeAddedEventListener(AppointmentsAddedListener listener) {
        subscribers.remove(AppointmentsAddedListener.class, listener);
    }

    @Override
    public void addUpdatedEventLister(AppointmentsUpdatedListener listener) {
        subscribers.add(AppointmentsUpdatedListener.class, listener);
    }

    @Override
    public void removeUpdatedEventListener(AppointmentsUpdatedListener listener) {
        subscribers.remove(AppointmentsUpdatedListener.class, listener);
    }
}
