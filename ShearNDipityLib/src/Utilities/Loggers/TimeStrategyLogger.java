package Utilities.Loggers;

import Utilities.Recorders.IRecorder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStrategyLogger extends LocaleStrategyLogger {

    public TimeStrategyLogger(IRecorder recorder) {
        super(recorder);
    }

    @Override
    String formatDate() {
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }
}
