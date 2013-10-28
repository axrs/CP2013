package dao.restDAO.events;

import java.util.EventObject;

public class Result extends EventObject {
    private int status = 0;
    private String response = "";

    public Result(Object source) {
        super(source);
    }

    public Result(Object source, int status, String response) {
        super(source);
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
