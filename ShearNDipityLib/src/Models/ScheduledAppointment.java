package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xander on 8/29/13.
 */
public class ScheduledAppointment extends Appointment {

    private String appTypeDescription;
    private String appTypeDuration;
    private String contSurname;
    private String contForename;
    private String servSurname;
    private String servForename;
    private String start;
    private String end;
    private String title;
    private String color;
    private String staff;

    public ScheduledAppointment() {
    }

    public String getAppTypeDescription() {
        return appTypeDescription;
    }

    public void setAppTypeDescription(String appTypeDescription) {
        this.appTypeDescription = appTypeDescription;
    }

    public String getAppTypeDuration() {
        return appTypeDuration;
    }

    public void setAppTypeDuration(String appTypeDuration) {
        this.appTypeDuration = appTypeDuration;
    }

    public String getContSurname() {
        return contSurname;
    }

    public void setContSurname(String contSurname) {
        this.contSurname = contSurname;
    }

    public String getContForename() {
        return contForename;
    }

    public void setContForename(String contForename) {
        this.contForename = contForename;
    }

    public String getServSurname() {
        return servSurname;
    }

    public void setServSurname(String servSurname) {
        this.servSurname = servSurname;
    }

    public String getServForename() {
        return servForename;
    }

    public void setServForename(String servForename) {
        this.servForename = servForename;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public Date getStartDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(start);
    }

    public Date getEndDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(end);
    }
}
