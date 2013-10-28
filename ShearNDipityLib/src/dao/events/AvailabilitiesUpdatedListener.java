package dao.events;

import java.util.EventListener;

public interface AvailabilitiesUpdatedListener extends EventListener {
    public void updated(UpdatedEvent event);
}

