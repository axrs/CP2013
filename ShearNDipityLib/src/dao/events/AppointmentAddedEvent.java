package dao.events;

import Models.Appointment;

import java.util.EventObject;

public class AppointmentAddedEvent extends EventObject {

    private Appointment appointment;

    public AppointmentAddedEvent(Object source, Appointment contact) {
        super(source);
        this.appointment = contact;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
