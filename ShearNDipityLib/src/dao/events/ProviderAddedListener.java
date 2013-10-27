package dao.events;

import java.util.EventListener;

public interface ProviderAddedListener extends EventListener {
    public void added(ProviderAddedEvent event);
}
