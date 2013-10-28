package dao.restDAO.models;

import models.Appointment;
import dao.IAppointmentDAO;
import dao.events.AppointmentsAddedListener;
import dao.events.AppointmentsUpdatedListener;
import dao.restDAO.Publisher;
import dao.restDAO.listeners.ResultListener;
import dao.restDAO.stores.AppointmentStore;

public class AppointmentDAO extends Publisher implements IAppointmentDAO {
    private static AppointmentDAO instance = null;
    private static AppointmentStore store = null;

    protected AppointmentDAO() {

    }

    public static AppointmentDAO getInstance() {
        if (instance == null) {
            instance = new AppointmentDAO();
            store = new AppointmentStore();
        }
        return instance;
    }

    @Override
    public Appointment[] getStore() {
        return new Appointment[0];
    }

    @Override
    public Appointment get(int id) {
        return null;
    }

    @Override
    public void create(Appointment contact, ResultListener listener) {

    }

    @Override
    public void create(Appointment contact) {

    }

    @Override
    public void update(Appointment contact, ResultListener listener) {

    }

    @Override
    public void update(Appointment contact) {

    }

    @Override
    public void remove(Appointment contact, ResultListener listener) {

    }

    @Override
    public void remove(Appointment contact) {

    }

    @Override
    public void remove(int id, ResultListener listener) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void addAddedEventListener(AppointmentsAddedListener listener) {

    }

    @Override
    public void removeAddedEventListener(AppointmentsAddedListener listener) {

    }

    @Override
    public void addUpdatedEventLister(AppointmentsUpdatedListener listener) {

    }

    @Override
    public void removeUpdatedEventListener(AppointmentsUpdatedListener listener) {

    }
}
