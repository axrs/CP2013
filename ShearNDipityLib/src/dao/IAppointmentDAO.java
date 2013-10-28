package dao;

import models.Appointment;
import dao.events.AppointmentsAddedListener;
import dao.events.AppointmentsUpdatedListener;
import dao.restDAO.listeners.ResultListener;

public interface IAppointmentDAO {

    public Appointment[] getStore();

    public Appointment get(int id);

    public void create(Appointment contact, ResultListener listener);

    public void create(Appointment contact);

    public void update(Appointment contact, ResultListener listener);

    public void update(Appointment contact);

    public void remove(Appointment contact, ResultListener listener);

    public void remove(Appointment contact);

    public void remove(int id, ResultListener listener);

    public void remove(int id);

    public void addAddedEventListener(AppointmentsAddedListener listener);

    public void removeAddedEventListener(AppointmentsAddedListener listener);

    public void addUpdatedEventLister(AppointmentsUpdatedListener listener);

    public void removeUpdatedEventListener(AppointmentsUpdatedListener listener);
}
