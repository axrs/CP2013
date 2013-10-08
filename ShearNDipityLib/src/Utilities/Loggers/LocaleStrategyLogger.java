package Utilities.Loggers;

import Utilities.Recorders.IRecorder;

import java.text.DateFormat;
import java.util.Date;

public class LocaleStrategyLogger extends StrategyLogger {

    public LocaleStrategyLogger(IRecorder recorder) {
        super(recorder);
    }

    @Override
    protected String format(String message) {
        return String.format("%s\t%s", formatDate(), message);
    }

    String formatDate() {
        return DateFormat.getDateInstance().format(new Date());
    }
}
