package dao.events;

import java.util.EventListener;

public interface AppointmentsAddedListener extends EventListener {
    public void updated(UpdatedEvent event);
}

