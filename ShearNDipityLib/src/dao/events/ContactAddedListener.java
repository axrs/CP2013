package dao.events;

import java.util.EventListener;

public interface ContactAddedListener extends EventListener {
    public void added(ContactAddedEvent event);
}
