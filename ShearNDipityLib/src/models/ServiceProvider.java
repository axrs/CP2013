package models;

import javafx.scene.paint.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 25/08/13
 * Time: 5:40 PM
 * <p/>
 * ServiceProvider, extends Contact to for a service provider.  Each Service Provider must have an Initiated value.
 * Each Service Provider requires ServiceHours, in the form of an array of ServiceHours objects.
 */
public class ServiceProvider extends Contact {

    private int providerId;
    private String biography;
    private String portrait;
    private String initiated = "";
    private String terminated = "";
    private int isActive;
    private String color = "#006dcc";
    private List<ServiceHours> hours;

    public ServiceProvider() {
        initialiseServiceHours();
    }

    public ServiceProvider(String contForename, String contSurname, String servInitiated) {
        super(contForename, contSurname);
        this.initiated = servInitiated;
        initialiseServiceHours();
    }

    public String getColorCode() {
        return color;
    }

    public Color getColor() {
        return Color.web(color);
    }

    public void setColor(Color value) {
        color = String.format("#%02X%02X%02X",
                (int) (value.getRed() * 255),
                (int) (value.getGreen() * 255),
                (int) (value.getBlue() * 255));
    }

    public String getFullName() {
        return getName() + " " + getSurname();
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public List<ServiceHours> getHours() {
        return hours;
    }

    public void setHours(List<ServiceHours> hours) {
        this.hours = hours;
    }

    public Date getServInitiatedDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.initiated);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public void setServInitiatedDate(Date date) {
        this.initiated = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String getInitiated() {
        return initiated;
    }

    public void setInitiated(String initiated) {
        this.initiated = initiated;
    }

    public Date getServTerminatedDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.terminated);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public void setServTerminatedDate(Date date) {
        this.terminated = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String getTerminated() {
        return terminated;
    }

    public void setTerminated(String terminated) {
        this.terminated = terminated;
    }

    public int getActive() {
        return isActive;
    }

    public void setActive(int active) {
        this.isActive = active;
    }

    public void initialiseServiceHours() {
        hours = new ArrayList<ServiceHours>(7);
        for (int i = 0; i < 7; i++) {
            hours.add(i, new ServiceHours(i, "00:00", "00:00", "00:00", "00:00"));
        }
    }

    public ServiceHours getDayHours(int dayNum) {
        return this.hours.get(dayNum);
    }

    public void setDayHours(int dayNum, ServiceHours servHours) {
        this.hours.set(dayNum, servHours);
    }
}
