package Utilities.Loggers;

import Utilities.Recorders.IRecorder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStrategyLogger extends LocaleStrategyLogger {
    public DateStrategyLogger(IRecorder recorder) {
        super(recorder);
    }

    @Override
    String formatDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
