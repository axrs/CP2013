package dao.events;

import java.util.EventListener;

public interface UserUpdatedListener extends EventListener {
    public void updated(UpdatedEvent event);
}

