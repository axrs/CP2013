package dao.rest.stores;

import Models.Appointment;
import dao.DataStore;

import java.util.Arrays;

public class AppointmentStore extends DataStore {
    @Override
    public Appointment get(int id) {
        return (Appointment) super.get(id);
    }

    public Appointment[] getValues() {
        return Arrays.copyOf(super.getValues(), super.getValues().length, Appointment[].class);
    }

    public void update(int id, Appointment appointment) {
        super.update(id, appointment);
    }

    public void add(int id, Appointment appointment) {
        super.add(id, appointment);
    }
}
