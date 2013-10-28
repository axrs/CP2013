package dao.events;

import java.util.EventListener;

public interface AppointmentsAddedListener extends EventListener {
    public void added(AppointmentAddedEvent event);
}

