package dao.events;

import java.util.EventListener;

public interface ContactsUpdatedListener extends EventListener {
    public void updated(UpdatedEvent event);
}

