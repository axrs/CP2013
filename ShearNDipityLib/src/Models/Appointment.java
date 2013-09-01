package Models;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 9:22 PM
 * <p/>
 * Appointment class - Requires all fields set upon creation. appTypeId, contId and servId are all foreign keys for the
 * database and the appointment can not be displayed without the time or date.
 */
public class Appointment {
    private int appId;
    private int appTypeId;
    private int contId;
    private int servId;
    private String appDate;
    private String appTime;

    public Appointment() {
    }

    public Appointment(int appId, int appTypeId, int contId, int servId, String appDate, String appTime) {
        this.appId = appId;
        this.appTypeId = appTypeId;
        this.contId = contId;
        this.servId = servId;
        this.appDate = appDate;
        this.appTime = appTime;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(int appTypeId) {
        this.appTypeId = appTypeId;
    }

    public int getContId() {
        return contId;
    }

    public void setContId(int contId) {
        this.contId = contId;
    }

    public int getServId() {
        return servId;
    }

    public void setServId(int servId) {
        this.servId = servId;
    }

    public String getAppDateString() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public Date getAppDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.appDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setAppDate(java.util.Date date) {
        this.appDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }
}
