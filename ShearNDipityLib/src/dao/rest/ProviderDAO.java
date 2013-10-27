package dao.rest;

import Models.ServiceProvider;
import com.google.gson.Gson;
import dao.IProviderDAO;
import dao.events.ProviderAddedEvent;
import dao.events.ProviderAddedListener;
import dao.events.ProviderUpdatedListener;
import dao.events.UpdatedEvent;
import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;
import dao.rest.requests.Request;
import dao.rest.requests.providers.GetAllProvidersRequest;
import dao.rest.stores.ProviderDataStore;

public class ProviderDAO extends Publisher implements IProviderDAO {

    private static ProviderDAO instance = null;
    private static ProviderDataStore store = null;

    protected ProviderDAO() {
        Request r = new GetAllProvidersRequest();
        r.addResultListener(onGetAllProvidersResult());
        ActiveRESTClient.addRequest(r);
    }

    public static ProviderDAO getInstance() {
        if (instance == null) {
            instance = new ProviderDAO();
            store = new ProviderDataStore();
        }
        return instance;
    }

    public static ServiceProvider[] getMap() {
        return getInstance().getStore();
    }

    public ServiceProvider[] getStore() {
        return store.getValues();
    }

    @Override
    public ServiceProvider get(int id) {
        return null;
    }

    @Override
    public void create(ServiceProvider contact, ResultListener listener) {

    }

    @Override
    public void create(ServiceProvider contact) {

    }

    @Override
    public void update(ServiceProvider contact, ResultListener listener) {

    }

    @Override
    public void update(ServiceProvider contact) {

    }

    @Override
    public void remove(ServiceProvider contact, ResultListener listener) {

    }

    @Override
    public void remove(ServiceProvider contact) {

    }

    @Override
    public void remove(int id, ResultListener listener) {

    }

    @Override
    public void remove(int id) {

    }


    //** SERVER RESPONSE ACTIONS **//

    private ResultListener onGetAllProvidersResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 200) return;
                store.clear();
                ServiceProvider[] results = new Gson().fromJson(result.getResponse(), ServiceProvider[].class);
                for (ServiceProvider provider : results) {
                    store.add(provider.getProviderId(), provider);
                }
                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    //** EVENTS AND SUBSCRIBERS **//

    @Override
    public void addAddedEventListener(ProviderAddedListener listener) {
        subscribers.add(ProviderAddedListener.class, listener);
    }

    @Override
    public void removeAddedEventListener(ProviderAddedListener listener) {
        subscribers.remove(ProviderAddedListener.class, listener);
    }

    @Override
    public void addUpdatedEventLister(ProviderUpdatedListener listener) {
        subscribers.add(ProviderUpdatedListener.class, listener);
    }

    @Override
    public void removeUpdatedEventListener(ProviderUpdatedListener listener) {
        subscribers.remove(ProviderUpdatedListener.class, listener);
    }

    private void fireAdded(ProviderAddedEvent event) {
        ProviderAddedListener[] listeners = subscribers.getListeners(ProviderAddedListener.class);
        for (ProviderAddedListener listener : listeners) {
            listener.added(event);
        }
    }

    private void fireUpdated(UpdatedEvent event) {
        ProviderUpdatedListener[] listeners = subscribers.getListeners(ProviderUpdatedListener.class);
        for (ProviderUpdatedListener listener : listeners) {
            listener.updated(event);
        }
    }
}
