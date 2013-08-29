package Models;

import java.sql.Time;
import java.text.DateFormatSymbols;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 5:53 PM
 * <p/>
 * ServiceHours used by a ServiceProvider, to set available times for making appointments. Days are numbered from 0-6,
 * Monday being 0 and Sunday being 6.
 */
public class ServiceHours {

    private int servHrsDay;
    private String servHrsStart = "00:00";
    private String servHrsBreakStart = "00:00";
    private String servHrsBreakEnd = "00:00";
    private String servHrsEnd = "00:00";
    private String servHrsDayName;

    public ServiceHours() {
    }

    public ServiceHours(int servHrsDay) {
        this.servHrsDay = servHrsDay;
    }

    public ServiceHours(int servHrsDay, String servHrsStart, String servHrsBreakStart, String servHrsBreakEnd, String servHrsEnd) {
        this.servHrsDay = servHrsDay;
        this.servHrsStart = servHrsStart;
        this.servHrsBreakStart = servHrsBreakStart;
        this.servHrsBreakEnd = servHrsBreakEnd;
        this.servHrsEnd = servHrsEnd;
    }

    public int getServHrsDay() {
        return servHrsDay;
    }

    public void setServHrsDay(int servHrsDay) {
        this.servHrsDay = servHrsDay;
    }

    public String getServHrsStart() {
        return servHrsStart;
    }

    public void setServHrsStart(String servHrsStart) {
        this.servHrsStart = servHrsStart;
    }

    public String getServHrsBreakStart() {
        return servHrsBreakStart;
    }

    public void setServHrsBreakStart(String servHrsBreakStart) {
        this.servHrsBreakStart = servHrsBreakStart;
    }

    public String getServHrsBreakEnd() {
        return servHrsBreakEnd;
    }

    public void setServHrsBreakEnd(String servHrsBreakEnd) {
        this.servHrsBreakEnd = servHrsBreakEnd;
    }

    public String getServHrsEnd() {
        return servHrsEnd;
    }

    public void setServHrsEnd(String servHrsEnd) {
        this.servHrsEnd = servHrsEnd;
    }

    public String getServHrsDayName(){

        return new DateFormatSymbols().getWeekdays()[servHrsDay+1];
    }
}
