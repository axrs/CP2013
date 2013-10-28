package dao.events;

import models.Contact;
import models.ServiceProvider;

import java.util.EventObject;

public class ProviderAddedEvent extends EventObject {

    private ServiceProvider provider;

    public ProviderAddedEvent(Object source, ServiceProvider contact) {
        super(source);
        this.provider = contact;
    }

    public Contact getProvider() {
        return provider;
    }
}
