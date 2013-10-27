package dao;

import Models.ServiceProvider;
import dao.events.ProviderAddedListener;
import dao.events.ProviderUpdatedListener;
import dao.rest.listeners.ResultListener;

public interface IProviderDAO {

    public ServiceProvider[] getStore();

    public ServiceProvider get(int id);

    public void create(ServiceProvider contact, ResultListener listener);

    public void create(ServiceProvider contact);

    public void update(ServiceProvider contact, ResultListener listener);

    public void update(ServiceProvider contact);

    public void remove(ServiceProvider contact, ResultListener listener);

    public void remove(ServiceProvider contact);

    public void remove(int id, ResultListener listener);

    public void remove(int id);

    public void addAddedEventListener(ProviderAddedListener listener);

    public void removeAddedEventListener(ProviderAddedListener listener);

    public void addUpdatedEventLister(ProviderUpdatedListener listener);

    public void removeUpdatedEventListener(ProviderUpdatedListener listener);
}
