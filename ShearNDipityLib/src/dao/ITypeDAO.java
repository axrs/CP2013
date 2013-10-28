package dao;

import Models.AppointmentType;
import dao.events.TypeAddedListener;
import dao.events.TypeUpdatedListener;
import dao.restDAO.listeners.ResultListener;

public interface ITypeDAO {

    public AppointmentType[] getStore();

    public AppointmentType get(int id);

    public void create(AppointmentType type, ResultListener listener);

    public void create(AppointmentType type);

    public void update(AppointmentType type, ResultListener listener);

    public void update(AppointmentType type);

    public void remove(AppointmentType type, ResultListener listener);

    public void remove(AppointmentType type);

    public void remove(int id, ResultListener listener);

    public void remove(int id);

    public void addAddedEventListener(TypeAddedListener listener);

    public void removeAddedEventListener(TypeAddedListener listener);

    public void addUpdatedEventLister(TypeUpdatedListener listener);

    public void removeUpdatedEventListener(TypeUpdatedListener listener);
}
