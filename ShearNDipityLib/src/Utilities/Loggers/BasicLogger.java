package Utilities.Loggers;

import Utilities.Recorders.IRecorder;

public class BasicLogger implements ILogger {
    private IRecorder recorder;

    public BasicLogger(IRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void log(String message) {
        recorder.record(message);
    }
}


