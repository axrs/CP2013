package Models;

import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 5:53 PM
 *
 * ServiceHours used by a ServiceProvider, to set available times for making appointments. Days are numbered from 0-6,
 * Monday being 0 and Sunday being 6.
 */
public class ServiceHours {

    private int servId;
    private int servHrsDay;
    private Time servHrsStart;
    private Time servHrsBreakStart;
    private Time servHrsBreakEnd;
    private Time servHrsEnd;

    public ServiceHours() {
    }

    public ServiceHours(int servId, int servHrsDay) {
        this.servId = servId;
        this.servHrsDay = servHrsDay;
    }

    public ServiceHours(int servId, int servHrsDay, Time servHrsStart, Time servHrsBreakStart, Time servHrsBreakEnd, Time servHrsEnd) {
        this.servId = servId;
        this.servHrsDay = servHrsDay;
        this.servHrsStart = servHrsStart;
        this.servHrsBreakStart = servHrsBreakStart;
        this.servHrsBreakEnd = servHrsBreakEnd;
        this.servHrsEnd = servHrsEnd;
    }

    public int getServId() {
        return servId;
    }

    public void setServId(int servId) {
        this.servId = servId;
    }

    public int getServHrsDay() {
        return servHrsDay;
    }

    public void setServHrsDay(int servHrsDay) {
        this.servHrsDay = servHrsDay;
    }

    public Time getServHrsStart() {
        return servHrsStart;
    }

    public void setServHrsStart(Time servHrsStart) {
        this.servHrsStart = servHrsStart;
    }

    public Time getServHrsBreakStart() {
        return servHrsBreakStart;
    }

    public void setServHrsBreakStart(Time servHrsBreakStart) {
        this.servHrsBreakStart = servHrsBreakStart;
    }

    public Time getServHrsBreakEnd() {
        return servHrsBreakEnd;
    }

    public void setServHrsBreakEnd(Time servHrsBreakEnd) {
        this.servHrsBreakEnd = servHrsBreakEnd;
    }

    public Time getServHrsEnd() {
        return servHrsEnd;
    }

    public void setServHrsEnd(Time servHrsEnd) {
        this.servHrsEnd = servHrsEnd;
    }
}
