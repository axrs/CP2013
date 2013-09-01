package Models;

import java.sql.Date;
import java.sql.Time;

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

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }
}
