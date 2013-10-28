package client.controllers.utilities;

import utilities.ILogListener;
import utilities.LogEventDispatcher;
import utilities.loggers.strategies.DateTimeFormatStrategy;
import utilities.loggers.strategies.TimeFormatStrategy;
import utilities.loggers.ILogger;
import utilities.loggers.StrategyLogger;
import utilities.recorders.ConsoleRecorder;
import utilities.recorders.DatedFileStreamRecorder;
import utilities.recorders.SingletonCompositeRecorder;
import client.controllers.ICommand;

public class HookLoggerCommand implements ICommand {
    final StrategyLogger timeStampedLogger = new StrategyLogger(new ConsoleRecorder(), new TimeFormatStrategy());
    private String rootFolder = "./logs";

    public void setRootFolder(String path) {
        if (!path.isEmpty()) {
            rootFolder = path;
        }
    }

    @Override
    public void execute() {
        {
            SingletonCompositeRecorder scr = SingletonCompositeRecorder.getInstance();
            scr.add(new DatedFileStreamRecorder(rootFolder));
            final StrategyLogger logger = new StrategyLogger(scr, new DateTimeFormatStrategy());

            LogEventDispatcher.addListener(registerListener(timeStampedLogger));
            LogEventDispatcher.addListener(registerListener(logger));
        }
    }

    private ILogListener registerListener(final ILogger logger) {
        return new ILogListener() {
            @Override
            public void onLog(String message) {
                logger.log(message);
            }
        };
    }

}
