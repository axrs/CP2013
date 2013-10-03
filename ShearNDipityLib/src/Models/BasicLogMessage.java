package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicLogMessage extends LogMessage {
    private Date _timeStamp = new Date();
    private String _message = "";

    public BasicLogMessage(String message) {
        _message = message;
    }

    @Override
    public String toString() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss").format(_timeStamp);
        return String.format("%s | %s", timeStamp, _message);
    }
}
