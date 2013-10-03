package Models;

import Interfaces.ILogger;

import java.util.ArrayList;
import java.util.List;

public class CompositeLogger implements ILogger {

    private static CompositeLogger _instance = null;
    private List<ILogger> _loggers;

    protected CompositeLogger() {
        _loggers = new ArrayList<ILogger>();

    }

    public static CompositeLogger getInstance() {
        if (_instance == null) {
            _instance = new CompositeLogger();
        }
        return _instance;
    }

    @Override
    public void log(LogMessage m) {
        for (ILogger l : _loggers) {
            l.log(m);
        }
    }

    public void add(ILogger logger) {
        _loggers.add(logger);
    }

    public void remove(ILogger logger) {
        if (_loggers.contains(logger)) {
            _loggers.remove(logger);
        }
    }
}
