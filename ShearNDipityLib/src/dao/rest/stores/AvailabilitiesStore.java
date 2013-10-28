package dao.rest.stores;

import Models.Availability;
import dao.BasicStore;

import java.util.Arrays;


public class AvailabilitiesStore extends BasicStore {

    public Availability[] getValues() {
        return Arrays.copyOf(super.getValues(), super.getValues().length, Availability[].class);
    }

    public void update(Availability appointment) {
        super.update(appointment);
    }

    public void add(Availability appointment) {
        super.add(appointment);
    }

}
