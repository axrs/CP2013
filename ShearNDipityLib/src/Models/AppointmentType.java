package Models;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 9:15 PM
 *
 * Each appointment will require an appointment type.  The type will dictate the amount of time
 * required for it to fit into the scheduler.
 */

public class AppointmentType {

    private int appTypeId;
    private String appTypeDescription;
    private int appTypeDuration;
    private boolean appTypeAllDay;

    public AppointmentType() {
    }

    public AppointmentType(int appTypeId, String appTypeDescription, int appTypeDuration, boolean appTypeAllDay) {
        this.appTypeId = appTypeId;
        this.appTypeDescription = appTypeDescription;
        this.appTypeDuration = appTypeDuration;
        this.appTypeAllDay = appTypeAllDay;
    }

    public int getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(int appTypeId) {
        this.appTypeId = appTypeId;
    }

    public String getAppTypeDescription() {
        return appTypeDescription;
    }

    public void setAppTypeDescription(String appTypeDescription) {
        this.appTypeDescription = appTypeDescription;
    }

    public int getAppTypeDuration() {
        return appTypeDuration;
    }

    public void setAppTypeDuration(int appTypeDuration) {
        this.appTypeDuration = appTypeDuration;
    }

    public boolean isAppTypeAllDay() {
        return appTypeAllDay;
    }

    public void setAppTypeAllDay(boolean appTypeAllDay) {
        this.appTypeAllDay = appTypeAllDay;
    }
}
