package client.controllers.models;

import client.controllers.ICommand;
import client.scene.control.Agenda;
import dao.DAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class GetAvailabilitiesRangeCommand implements ICommand {

    private String start;
    private String end;

    public GetAvailabilitiesRangeCommand(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public GetAvailabilitiesRangeCommand(Agenda agenda) {
        Calendar displayedCalendar = agenda.getDisplayedCalendar();
        Date now = displayedCalendar.getTime();
        this.start = new SimpleDateFormat("yyyy-MM-dd").format(now);
        now.setTime(now.getTime() + (1000 * 60 * 60 * 24 * 7));
        this.end = new SimpleDateFormat("yyyy-MM-dd").format(now);
    }

    public GetAvailabilitiesRangeCommand(Date start, Date end) {
        this.start = new SimpleDateFormat("yyyy-MM-dd").format(start);
        this.end = new SimpleDateFormat("yyyy-MM-dd").format(end);
    }

    @Override
    public void execute() {
        DAO.getInstance().getAvailabilitiesDAO().update(start, end);
    }
}
