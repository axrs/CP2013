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
    protected int contID;
    protected int servID;
    protected Date appDate;
    protected Time appStartTime;

    public Appointment() {
    }

    public Appointment(int appTypeID, int contID, int servID, Date appDate, Time appStartTime) {
        this.appTypeID = appTypeID;
        this.contID = contID;
        this.servID = servID;
        this.appDate = appDate;
        this.appStartTime = appStartTime;
    }

    public void setAppointment() {
        try {
            this.appTypeID = appTypeID;
            this.contID = contID;
            this.servID = servID;
            this.appDate = appDate;
            this.appStartTime = appStartTime;

            throw new AppointmentException("Appointment details missing");
        }
        catch(AppointmentException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
