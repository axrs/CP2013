package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 29/08/13
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Availability {

    private String title;
    private int servId;
    private String color;
    private String start;
    private String end;
    private boolean allDay;

    public Availability() {
    }

    public Availability(String title, int servId, String color, String start, String end, boolean allDay) {
        this.title = title;
        this.servId = servId;
        this.color = color;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getServId() {
        return servId;
    }

    public void setServId(int servId) {
        this.servId = servId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public Date convertToDate(String dts) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").parse(dts);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return date;
    }
}