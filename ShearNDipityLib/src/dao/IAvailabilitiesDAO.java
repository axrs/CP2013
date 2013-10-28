package dao;

import Models.Availability;
import dao.events.AvailabilitiesUpdatedListener;
import dao.restDAO.listeners.ResultListener;

public interface IAvailabilitiesDAO {

    public Availability[] getStore();

    public Availability[] getProviderAvailabilities(int providerId);

    public void update(String start, String end, ResultListener listener);

    public void update(String start, String end);

    public void addUpdatedEventLister(AvailabilitiesUpdatedListener listener);

    public void removeUpdatedEventListener(AvailabilitiesUpdatedListener listener);

}
