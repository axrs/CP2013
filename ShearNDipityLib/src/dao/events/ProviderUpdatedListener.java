package dao.events;

import java.util.EventListener;

public interface ProviderUpdatedListener extends EventListener {
    public void updated(UpdatedEvent event);
}

