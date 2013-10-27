package dao.events;

import java.util.EventListener;

public interface TypeUpdatedListener extends EventListener {
    public void updated(UpdatedEvent event);
}

