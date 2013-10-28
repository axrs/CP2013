package dao.restDAO.stores;

import Models.AppointmentType;
import dao.DataStore;

import java.util.Arrays;

public class TypeDataStore extends DataStore {
    @Override
    public AppointmentType get(int id) {
        return (AppointmentType) super.get(id);
    }

    public AppointmentType[] getValues() {
        return Arrays.copyOf(super.getValues(), super.getValues().length, AppointmentType[].class);
    }

    public void update(int id, AppointmentType type) {
        super.update(id, type);
    }

    public void add(int id, AppointmentType type) {
        super.add(id, type);
    }
}
