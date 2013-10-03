package Models;

import Interfaces.ILogger;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class AbstractLogger implements ILogger {
    private LogMessage _message;

    public final void log(LogMessage m) {
        _message = m;
        prepare();
        writeMessage();
        finalise();
    }

    public abstract void prepare() throws FileNotFoundException;

    public abstract void writeMessage() throws IOException;

    public abstract void finalise();

    public LogMessage getMessage() {
        return _message;
    }
}
