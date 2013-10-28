package client.controllers.models;

import client.controllers.ICommand;
import dao.DAO;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GetAvailabilitiesRangeCommand implements ICommand {

    private String start;
    private String end;

    public GetAvailabilitiesRangeCommand(String start, String end) {
        this.start = start;
        this.end = end;
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
