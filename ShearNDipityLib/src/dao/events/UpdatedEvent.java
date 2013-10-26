package dao.events;

import java.util.EventObject;

public class UpdatedEvent extends EventObject {

    public UpdatedEvent(Object source) {
        super(source);
    }
}
