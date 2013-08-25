package Models;

import java.sql.Date;
import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 9:22 PM
 *
 * Appointment class - Requires all fields set upon creation. appTypeId, contId and servId are all foreign keys for the
 * database and the appointment can not be displayed without the time or date.
 */
public class Appointment {

    private int appTypeId;
    private int contId;
    private int servId;
    private Date appDate;
    private Time appTime;

    public Appointment() {
    }

    public Appointment(int appTypeId, int contId, int servId, Date appDate, Time appTime) {
        this.appTypeId = appTypeId;
        this.contId = contId;
        this.servId = servId;
        this.appDate = appDate;
        this.appTime = appTime;
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

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Time getAppTime() {
        return appTime;
    }

    public void setAppTime(Time appTime) {
        this.appTime = appTime;
    }
}
