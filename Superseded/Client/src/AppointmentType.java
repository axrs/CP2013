import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 12/08/13
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppointmentType {
    protected int appTypeID;
    protected String appTypeDesc;
    protected Time appTypeDura;
    protected boolean appTypeAllDay;

    public AppointmentType(int appTypeID, String appTypeDesc, Time appTypeDura, boolean appTypeAllDay) {
        this.appTypeID = appTypeID;
        this.appTypeDesc = appTypeDesc;
        this.appTypeDura = appTypeDura;
        this.appTypeAllDay = appTypeAllDay;
    }
}
