package dao.events;

import Models.AppointmentType;

import java.util.EventObject;

public class TypeAddedEvent extends EventObject {

    private AppointmentType type;

    public TypeAddedEvent(Object source, AppointmentType appointmentType) {
        super(source);
        this.type = appointmentType;
    }

    public AppointmentType getType() {
        return type;
    }
}
