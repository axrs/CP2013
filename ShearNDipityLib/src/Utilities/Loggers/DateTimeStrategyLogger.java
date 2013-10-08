package Utilities.Loggers;

import Utilities.Recorders.IRecorder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeStrategyLogger extends LocaleStrategyLogger {
    public DateTimeStrategyLogger(IRecorder recorder) {
        super(recorder);
    }

    @Override
    String formatDate() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }
}
