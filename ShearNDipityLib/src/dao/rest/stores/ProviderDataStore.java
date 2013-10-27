package dao.rest.stores;

import Models.ServiceProvider;
import dao.DataStore;

import java.util.Arrays;

public class ProviderDataStore extends DataStore {
    @Override
    public ServiceProvider get(int id) {
        return (ServiceProvider) super.get(id);
    }

    public ServiceProvider[] getValues() {
        return Arrays.copyOf(super.getValues(), super.getValues().length, ServiceProvider[].class);
    }

    public void update(int id, ServiceProvider provider) {
        super.update(id, provider);
    }

    public void add(int id, ServiceProvider provider) {
        super.add(id, provider);
    }
}
