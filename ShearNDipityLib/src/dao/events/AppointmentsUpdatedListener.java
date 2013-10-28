package dao.events;

import java.util.EventListener;

public interface AppointmentsUpdatedListener extends EventListener {
    public void updated(UpdatedEvent event);
}

