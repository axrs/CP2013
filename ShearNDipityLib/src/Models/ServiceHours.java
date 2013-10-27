package Models;

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

    private int day;
    private String start = "00:00";
    private String breakStart = "00:00";
    private String breakEnd = "00:00";
    private String end = "00:00";
    private String dayName;

    public ServiceHours() {
    }

    public ServiceHours(int servHrsDay) {
        this.day = servHrsDay;
    }

    public ServiceHours(int servHrsDay, String servHrsStart, String servHrsBreakStart, String servHrsBreakEnd, String servHrsEnd) {
        this.day = servHrsDay;
        this.start = servHrsStart;
        this.breakStart = servHrsBreakStart;
        this.breakEnd = servHrsBreakEnd;
        this.end = servHrsEnd;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(String breakStart) {
        this.breakStart = breakStart;
    }

    public String getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(String breakEnd) {
        this.breakEnd = breakEnd;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDayName() {

        return new DateFormatSymbols().getWeekdays()[day + 1];
    }
}
