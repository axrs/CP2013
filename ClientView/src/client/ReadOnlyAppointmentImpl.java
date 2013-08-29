package client;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import jfxtras.labs.scene.control.Agenda;

import java.util.Calendar;

import static jfxtras.labs.scene.control.CalendarPicker.quickFormatCalendar;

/**
 * Created by xander on 8/29/13.
 */
public class ReadOnlyAppointmentImpl implements Agenda.Appointment {
    final private ObjectProperty<Calendar> startTimeObjectProperty = new SimpleObjectProperty<Calendar>(this, "startTime");
    final private ObjectProperty<Calendar> endTimeObjectProperty = new SimpleObjectProperty<Calendar>(this, "endTime");
    final private ObjectProperty<Boolean> wholeDayObjectProperty = new SimpleObjectProperty<Boolean>(this, "wholeDay", false);
    final private ObjectProperty<String> summaryObjectProperty = new SimpleObjectProperty<String>(this, "summary");
    final private ObjectProperty<String> descriptionObjectProperty = new SimpleObjectProperty<String>(this, "description");
    final private ObjectProperty<String> locationObjectProperty = new SimpleObjectProperty<String>(this, "location");
    final private ObjectProperty<Agenda.AppointmentGroup> appointmentGroupObjectProperty = new SimpleObjectProperty<Agenda.AppointmentGroup>(this, "appointmentGroup");
    final private ObjectProperty<Integer> appIdObjectProperty = new SimpleObjectProperty<Integer>(this, "appId");

    public Integer getAppId() {
        return appIdObjectProperty.get();
    }

    public void setAppId(Integer appIdObjectProperty) {
        this.appIdObjectProperty.set(appIdObjectProperty);
    }

    public ObjectProperty<Integer> appIdProperty() {
        return appIdObjectProperty;
    }

    /**
     * StartTime:
     */
    public ObjectProperty<Calendar> startTimeProperty() {
        return startTimeObjectProperty;
    }

    public Calendar getStartTime() {
        return startTimeObjectProperty.getValue();
    }

    public void setStartTime(Calendar value) {
        //startTimeObjectProperty.setValue(value);
    }

    public ReadOnlyAppointmentImpl withStartTime(Calendar value) {
        startTimeObjectProperty.setValue(value);
        return this;
    }

    /**
     * EndTime:
     */
    public ObjectProperty<Calendar> endTimeProperty() {
        return endTimeObjectProperty;
    }

    public Calendar getEndTime() {
        return endTimeObjectProperty.getValue();
    }

    public void setEndTime(Calendar value) {
        //endTimeObjectProperty.setValue(value);
    }

    public ReadOnlyAppointmentImpl withEndTime(Calendar value) {
        endTimeObjectProperty.setValue(value);
        return this;
    }

    /**
     * WholeDay:
     */
    public ObjectProperty<Boolean> wholeDayProperty() {
        return wholeDayObjectProperty;
    }

    public Boolean isWholeDay() {
        return wholeDayObjectProperty.getValue();
    }

    public void setWholeDay(Boolean value) {
        //wholeDayObjectProperty.setValue(value);
    }

    public ReadOnlyAppointmentImpl withWholeDay(Boolean value) {
        setWholeDay(value);
        return this;
    }

    /**
     * Summary:
     */
    public ObjectProperty<String> summaryProperty() {
        return summaryObjectProperty;
    }

    public String getSummary() {
        return summaryObjectProperty.getValue();
    }

    public void setSummary(String value) {
        summaryObjectProperty.setValue(value);
    }

    public ReadOnlyAppointmentImpl withSummary(String value) {
        setSummary(value);
        return this;
    }

    /**
     * Description:
     */
    public ObjectProperty<String> descriptionProperty() {
        return descriptionObjectProperty;
    }

    public String getDescription() {
        return descriptionObjectProperty.getValue();
    }

    public void setDescription(String value) {
        descriptionObjectProperty.setValue(value);
    }

    public ReadOnlyAppointmentImpl withDescription(String value) {
        setDescription(value);
        return this;
    }

    /**
     * Location:
     */
    public ObjectProperty<String> locationProperty() {
        return locationObjectProperty;
    }

    public String getLocation() {
        return locationObjectProperty.getValue();
    }

    public void setLocation(String value) {
        locationObjectProperty.setValue(value);
    }

    public ReadOnlyAppointmentImpl withLocation(String value) {
        setLocation(value);
        return this;
    }

    /**
     * AppointmentGroup:
     */
    public ObjectProperty<Agenda.AppointmentGroup> appointmentGroupProperty() {
        return appointmentGroupObjectProperty;
    }

    public Agenda.AppointmentGroup getAppointmentGroup() {
        return appointmentGroupObjectProperty.getValue();
    }

    public void setAppointmentGroup(Agenda.AppointmentGroup value) {
        appointmentGroupObjectProperty.setValue(value);
    }

    public ReadOnlyAppointmentImpl withAppointmentGroup(Agenda.AppointmentGroup value) {
        setAppointmentGroup(value);
        return this;
    }

    public String toString() {
        return super.toString()
                + ", "
                + quickFormatCalendar(this.getStartTime())
                + " - "
                + quickFormatCalendar(this.getEndTime())
                ;
    }
}
