package dao.restDAO.models;

import models.ServiceProvider;
import com.google.gson.Gson;
import dao.IProviderDAO;
import dao.events.ProviderAddedEvent;
import dao.events.ProviderAddedListener;
import dao.events.ProviderUpdatedListener;
import dao.events.UpdatedEvent;
import dao.restDAO.client.ActiveRESTClient;
import dao.restDAO.Publisher;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import dao.restDAO.requests.Request;
import dao.restDAO.requests.providers.*;
import dao.restDAO.stores.ProviderDataStore;

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
        ServiceProvider provider = store.get(id);

        if (provider == null) {
            Request r = new GetProviderRequest(id);
            r.addResultListener(onGetProviderResult());
            ActiveRESTClient.addRequest(r);
        }

        return provider;
    }

    @Override
    public void create(ServiceProvider provider, ResultListener listener) {
        Request r = new CreateProviderRequest(provider);
        if (listener != null) {
            r.addResultListener(listener);
        }
        r.addResultListener(onProviderUpdatedResult());
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void create(ServiceProvider serviceProvider) {
        create(serviceProvider, null);
    }

    @Override
    public void update(ServiceProvider serviceProvider, ResultListener listener) {
        Request r = new UpdateProviderRequest(serviceProvider);
        r.addResultListener(onProviderUpdatedResult());
        if (listener != null) {
            r.addResultListener(listener);
        }
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void update(ServiceProvider serviceProvider) {
        update(serviceProvider, null);
    }

    @Override
    public void remove(ServiceProvider serviceProvider, ResultListener listener) {
        remove(serviceProvider.getProviderId(), listener);
    }

    @Override
    public void remove(ServiceProvider serviceProvider) {
        remove(serviceProvider.getProviderId(), null);
    }

    @Override
    public void remove(int id, ResultListener listener) {
        Request r = new RemoveProviderRequest(id);
        r.addResultListener(onProviderRemoved(id));
        if (listener != null) {
            r.addResultListener(listener);
        }
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void remove(int id) {
        remove(id, null);
    }


    //** SERVER RESPONSE ACTIONS **//

    private ResultListener onProviderRemoved(final int id) {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 202) return;
                store.remove(id);
                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    private ResultListener onGetProviderResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                ServiceProvider provider;
                if (result.getStatus() != 200) return;

                provider = new Gson().fromJson(result.getResponse(), ServiceProvider.class);
                if (provider.getProviderId() != 0) {
                    store.add(provider.getProviderId(), provider);
                }

                //Fire events for individual contact added, and all contacts list updated
                if (provider != null) {
                    fireAdded(new ProviderAddedEvent(this, provider));
                    fireUpdated(new UpdatedEvent(this));
                }
            }
        };
    }

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

    private ResultListener onProviderUpdatedResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 201 && result.getStatus() != 202) return;

                Request r = new GetAllProvidersRequest();
                r.addResultListener(onGetAllProvidersResult());
                ActiveRESTClient.addRequest(r);
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
