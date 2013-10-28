package dao.restDAO.models;

import models.AppointmentType;
import com.google.gson.Gson;
import dao.ITypeDAO;
import dao.events.TypeAddedEvent;
import dao.events.TypeAddedListener;
import dao.events.TypeUpdatedListener;
import dao.events.UpdatedEvent;
import dao.restDAO.client.ActiveRESTClient;
import dao.restDAO.Publisher;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import dao.restDAO.requests.Request;
import dao.restDAO.requests.types.*;
import dao.restDAO.stores.TypeDataStore;

public class TypeDAO extends Publisher implements ITypeDAO {

    private static TypeDAO instance = null;
    private static TypeDataStore store = null;

    protected TypeDAO() {
        Request r = new GetAllTypesRequest();
        r.addResultListener(onGetAllTypeResult());
        ActiveRESTClient.addRequest(r);
    }

    public static TypeDAO getInstance() {
        if (instance == null) {
            instance = new TypeDAO();
            store = new TypeDataStore();
        }
        return instance;
    }

    public static AppointmentType[] getMap() {
        return getInstance().getStore();
    }

    public AppointmentType[] getStore() {
        return store.getValues();
    }

    @Override
    public AppointmentType get(int id) {
        AppointmentType provider = store.get(id);

        if (provider == null) {
            Request r = new GetTypeRequest(id);
            r.addResultListener(onGetTypeRequest());
            ActiveRESTClient.addRequest(r);
        }

        return provider;
    }

    @Override
    public void create(AppointmentType type, ResultListener listener) {
        Request r = new CreateTypeRequest(type);
        if (listener != null) {
            r.addResultListener(listener);
        }
        r.addResultListener(onTypeUpdatedResult());
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void create(AppointmentType type) {
        create(type, null);
    }

    @Override
    public void update(AppointmentType type, ResultListener listener) {
        Request r = new UpdateTypeRequest(type);
        r.addResultListener(onTypeUpdatedResult());
        if (listener != null) {
            r.addResultListener(listener);
        }
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void update(AppointmentType type) {
        update(type, null);
    }

    @Override
    public void remove(AppointmentType type, ResultListener listener) {
        remove(type.getTypeId(), listener);
    }

    @Override
    public void remove(AppointmentType type) {
        remove(type, null);
    }

    @Override
    public void remove(int id, ResultListener listener) {
        Request r = new RemoveTypeRequest(id);
        r.addResultListener(onRemoveTypeRequest(id));
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

    private ResultListener onRemoveTypeRequest(final int id) {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 202) return;
                store.remove(id);
                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    private ResultListener onGetTypeRequest() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                AppointmentType type;
                if (result.getStatus() != 200) return;

                type = new Gson().fromJson(result.getResponse(), AppointmentType.class);
                if (type.getTypeId() != 0) {
                    store.add(type.getTypeId(), type);
                }

                //Fire events for individual contact added, and all contacts list updated
                if (type != null) {
                    fireAdded(new TypeAddedEvent(this, type));
                    fireUpdated(new UpdatedEvent(this));
                }
            }
        };
    }

    private ResultListener onGetAllTypeResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 200) return;
                store.clear();
                AppointmentType[] results = new Gson().fromJson(result.getResponse(), AppointmentType[].class);
                for (AppointmentType type : results) {
                    store.add(type.getTypeId(), type);
                }

                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    private ResultListener onTypeUpdatedResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 201 && result.getStatus() != 202) return;

                Request r = new GetAllTypesRequest();
                r.addResultListener(onGetAllTypeResult());
                ActiveRESTClient.addRequest(r);
            }
        };
    }

    //** EVENTS AND SUBSCRIBERS **//

    @Override
    public void addAddedEventListener(TypeAddedListener listener) {
        subscribers.add(TypeAddedListener.class, listener);
    }

    @Override
    public void removeAddedEventListener(TypeAddedListener listener) {
        subscribers.remove(TypeAddedListener.class, listener);
    }

    @Override
    public void addUpdatedEventLister(TypeUpdatedListener listener) {
        subscribers.add(TypeUpdatedListener.class, listener);
    }

    @Override
    public void removeUpdatedEventListener(TypeUpdatedListener listener) {
        subscribers.remove(TypeUpdatedListener.class, listener);
    }

    private void fireAdded(TypeAddedEvent event) {
        TypeAddedListener[] listeners = subscribers.getListeners(TypeAddedListener.class);
        for (TypeAddedListener listener : listeners) {
            listener.added(event);
        }
    }

    private void fireUpdated(UpdatedEvent event) {
        TypeUpdatedListener[] listeners = subscribers.getListeners(TypeUpdatedListener.class);
        for (TypeUpdatedListener listener : listeners) {
            listener.updated(event);
        }
    }


}
