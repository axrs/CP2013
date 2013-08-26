import com.google.gson.Gson;

import java.sql.Date;
import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 12/08/13
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Appointment {

    protected int appTypeID;
    protected Contact contact;
    protected ServiceProvider serviceProvider;
    protected Date appDate;
    protected Time appStartTime;

    public Appointment() {
    }

    public Appointment(int appTypeID, Contact contact, ServiceProvider serviceProvider, Date appDate, Time appStartTime) {
        this.appTypeID = appTypeID;
        this.contact = contact;
        this.serviceProvider = serviceProvider;
        this.appDate = appDate;
        this.appStartTime = appStartTime;
    }

    /**
     * Turns Appointment object into Json object for use by server.
     * @return a Json serialization of Appointment.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Returns a Appointment object generated from Json object, sent from server.
     * @param data
     * @return
     */
    public static Appointment fromJson(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, Appointment.class);
    }
}
