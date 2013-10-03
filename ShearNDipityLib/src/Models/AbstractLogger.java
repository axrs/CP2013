package Models;

import Interfaces.ILogger;

public abstract class AbstractLogger implements ILogger {
    private LogMessage _message;

    public final void log(LogMessage m) {
        _message = m;
        prepare();
        writeMessage();
        finalise();
    }

    public abstract void prepare();

    public abstract void writeMessage();

    public abstract void finalise();

    public LogMessage getMessage() {
        return _message;
    }
}
