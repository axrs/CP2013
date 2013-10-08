package Utilities.Loggers;

import Utilities.Recorders.IRecorder;

public abstract class StrategyLogger implements ILogger {
    IRecorder recorder = null;

    public StrategyLogger(IRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void log(String message) {
        recorder.record(format(message));
    }

    protected abstract String format(String message);
}
