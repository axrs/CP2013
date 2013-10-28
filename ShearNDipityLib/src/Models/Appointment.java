package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 9:22 PM
 * <p/>
 * Appointment class - Requires all fields set upon creation. typeId, contactId and providerId are all foreign keys for the
 * database and the appointment can not be displayed without the time or date.
 */
public class Appointment {
    private int appointmentId;
    private int typeId;
    private int contactId;
    private int providerId;
    private String date = "";
    private String time = "";
    private String endTime = "";
    private String description = "";

    public Appointment() {
    }

    public Appointment(int id, int typeId, int contactId, int providerId, String date, String time) {
        this.appointmentId = id;
        this.typeId = typeId;
        this.contactId = contactId;
        this.providerId = providerId;
        this.date = date;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getAppDateString() {
        return date;
    }

    public Date getDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.date);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAppDate(java.util.Date date) {
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public Date getStartDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(date + " " + time);
    }

    public Date getEndDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(date + " " + endTime);
    }

}
