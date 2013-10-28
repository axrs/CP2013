package dao;

import dao.events.AppointmentsAddedListener;
import dao.events.AppointmentsUpdatedListener;
import dao.restDAO.listeners.ResultListener;
import models.Appointment;

public interface IAppointmentDAO {

    public Appointment[] getStore();

    public Appointment get(int id);

    public void create(Appointment appointment, ResultListener listener);

    public void create(Appointment appointment);

    public void update(Appointment appointment, ResultListener listener);

    public void update(Appointment appointment);

    public void remove(Appointment appointment, ResultListener listener);

    public void remove(Appointment appointment);

    public void remove(int id, ResultListener listener);

    public void remove(int id);

    public void addAddedEventListener(AppointmentsAddedListener listener);

    public void removeAddedEventListener(AppointmentsAddedListener listener);

    public void addUpdatedEventLister(AppointmentsUpdatedListener listener);

    public void removeUpdatedEventListener(AppointmentsUpdatedListener listener);
}
