package scene.control;

import javafx.scene.control.ComboBox;

import java.util.Calendar;
import java.util.Date;


public class TimeComboBox extends ComboBox {

    private int step = 15;
    private int startMinutes;
    private int endMinutes;


    public TimeComboBox() {
        setStartTime(new Date());
    }

    public TimeComboBox(int step) {
        setStep(step);
    }

    public TimeComboBox(Date start, int step) {
        setStartTime(start);
        setStep(step);
    }

    public TimeComboBox(Date start, Date end, int step) {
        setStartTime(start);
        setEndTime(end);
        setStep(step);
    }

    /**
     * Gets the current minute duration of today
     *
     * @param time Current Time
     * @return Total minute count of today so far
     */
    private static int getMinutesOfDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return (cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE));
    }

    private static int roundUp(int value, int step) {
        return ((int) Math.floor(value / step)) * step;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step > 0 && step < 1440) {
            this.step = step;
        }
        populateValues();
    }

    public void setStartTime(Date time) {
        startMinutes = roundUp(getMinutesOfDay(time), step);

        if (endMinutes <= startMinutes) {
            endMinutes = 1440;
        }
        populateValues();
    }

    public void setEndTime(Date time) {
        int end = roundUp(getMinutesOfDay(time), step);
        if (end <= startMinutes) {
            endMinutes = 1440;
        } else {
            endMinutes = end;
        }
        populateValues();
    }

    private long duration() {
        return endMinutes - startMinutes;
    }

    private void populateValues() {

        this.getItems().clear();

        int nextStep;
        for (int i = 0; i <= duration(); i += step) {

            nextStep = startMinutes + i;

            if (nextStep > 1440) {
                nextStep -= 1440;
            }
            this.getItems().add(String.format("%02d:%02d", (int) nextStep / 60, (int) nextStep % 60));
        }

    }
}
