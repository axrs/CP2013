package client.controllers.utilities;

import client.controllers.ICommand;
import jfxtras.labs.scene.control.Agenda;

import java.util.Calendar;
import java.util.Date;

public class OffsetAgendaViewCommand implements ICommand {
    private Agenda agendaView = null;
    private int dayOffset = 0;

    public OffsetAgendaViewCommand(Agenda agendaView, int dayOffset) {
        this.agendaView = agendaView;
        this.dayOffset = dayOffset;
    }

    @Override
    public void execute() {
        Calendar displayedCalendar = agendaView.getDisplayedCalendar();
        Date currentCalendarTime = displayedCalendar.getTime();
        currentCalendarTime.setTime(currentCalendarTime.getTime() + (dayOffset * 24 * 60 * 60 * 1000));

        displayedCalendar.setTime(currentCalendarTime);
        agendaView.setDisplayedCalendar(displayedCalendar);
        agendaView.refresh();
    }
}
