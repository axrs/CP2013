package utilities.loggers;

import utilities.recorders.IRecorder;

public class BasicLogger implements ILogger {
    IRecorder recorder;

    public BasicLogger(IRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void log(String message) {
        recorder.record(message);
    }
}


