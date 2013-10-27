package dao.events;

import java.util.EventListener;

public interface TypeAddedListener extends EventListener {
    public void added(TypeAddedEvent event);
}
