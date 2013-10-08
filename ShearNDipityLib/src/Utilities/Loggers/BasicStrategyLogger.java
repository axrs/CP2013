package Utilities.Loggers;

import Utilities.Recorders.IRecorder;

public class BasicStrategyLogger extends StrategyLogger {
    public BasicStrategyLogger(IRecorder recorder) {
        super(recorder);
    }

    @Override
    protected String format(String message) {
        return message;
    }
}


