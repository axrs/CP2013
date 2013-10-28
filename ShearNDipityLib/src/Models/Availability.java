package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 29/08/13
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Availability {

    private int providerId;
    private String date;
    private String start;
    private String end;
    private String color;

    public Availability() {
    }

    public Date getDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.date);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(java.util.Date date) {
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getStartDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(date + " " + start);
    }

    public Date getEndDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(date + " " + end);
    }
}
