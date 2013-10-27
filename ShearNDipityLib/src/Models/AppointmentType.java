package Models;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 9:15 PM
 * <p/>
 * Each appointment will require an appointment type.  The type will dictate the amount of time
 * required for it to fit into the scheduler.
 */

public class AppointmentType {

    private int typeId;
    private String description;
    private String duration;
    private int isAllDay;

    public AppointmentType() {
    }

    public AppointmentType(int appTypeId, String appTypeDescription, String appTypeDuration, int appTypeAllDay) {
        this.typeId = appTypeId;
        this.description = appTypeDescription;
        this.duration = appTypeDuration;
        this.isAllDay = appTypeAllDay;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        System.out.println(description);
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean getIsAllDay() {
        return isAllDay == 1;
    }

    public void setIsAllDay(boolean isAllDay) {
        this.isAllDay = isAllDay ? 1 : 0;
    }
}
