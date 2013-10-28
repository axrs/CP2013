package dao.restDAO.models;

import Models.Availability;
import com.google.gson.Gson;
import dao.IAvailabilitiesDAO;
import dao.events.AvailabilitiesUpdatedListener;
import dao.events.UpdatedEvent;
import dao.restDAO.client.ActiveRESTClient;
import dao.restDAO.Publisher;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import dao.restDAO.requests.Request;
import dao.restDAO.requests.availabilities.GetAllAvailabilitiesRequest;
import dao.restDAO.stores.AvailabilitiesStore;

import java.util.ArrayList;

public class AvailabilityDAO extends Publisher implements IAvailabilitiesDAO {

    private static AvailabilityDAO instance = null;
    private static AvailabilitiesStore store = null;

    protected AvailabilityDAO() {
    }

    public static AvailabilityDAO getInstance() {
        if (instance == null) {
            instance = new AvailabilityDAO();
            store = new AvailabilitiesStore();
        }
        return instance;
    }

    public static Availability[] getMap() {
        return getInstance().getStore();
    }

    @Override
    public Availability[] getProviderAvailabilities(int providerId) {
        ArrayList<Availability> results = new ArrayList<Availability>();

        Availability[] all = store.getValues();

        for (Availability a : all) {
            if (a.getProviderId() == providerId) {
                results.add(a);
            }
        }
        return (Availability[]) results.toArray();
    }

    @Override
    public Availability[] getStore() {
        return store.getValues();
    }

    @Override
    public void update(String start, String end) {
        update(start, end, null);
    }

    @Override
    public void update(String start, String end, ResultListener listener) {
        Request r = new GetAllAvailabilitiesRequest(start, end);
        if (listener != null) {
            r.addResultListener(listener);
        }
        r.addResultListener(onGetAvailabilities());

        ActiveRESTClient.addRequest(r);
    }

    private ResultListener onGetAvailabilities() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() != 200) return;
                store.clear();

                Availability[] results = new Gson().fromJson(result.getResponse(), Availability[].class);
                for (Availability provider : results) {
                    store.add(provider);
                }

                fireUpdated(new UpdatedEvent(this));
            }
        };
    }

    private void fireUpdated(UpdatedEvent event) {
        AvailabilitiesUpdatedListener[] listeners = subscribers.getListeners(AvailabilitiesUpdatedListener.class);
        for (AvailabilitiesUpdatedListener listener : listeners) {
            listener.updated(event);
        }
    }

    @Override
    public void addUpdatedEventLister(AvailabilitiesUpdatedListener listener) {
        subscribers.add(AvailabilitiesUpdatedListener.class, listener);

    }

    @Override
    public void removeUpdatedEventListener(AvailabilitiesUpdatedListener listener) {
        subscribers.remove(AvailabilitiesUpdatedListener.class, listener);
    }
}
