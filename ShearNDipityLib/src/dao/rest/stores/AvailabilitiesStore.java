package dao.rest.stores;

import Models.Availability;
import dao.BasicStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    public void addAll(List<Availability> values) {
        List<Object> l = new ArrayList<Object>();
        l.addAll(values);
        super.addAll(l);
    }
}
