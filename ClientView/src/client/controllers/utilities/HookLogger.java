package client.controllers.utilities;

import Utilities.ILogListener;
import Utilities.LogEventDispatcher;
import Utilities.Loggers.FormatStrategies.DateTimeFormatStrategy;
import Utilities.Loggers.FormatStrategies.TimeFormatStrategy;
import Utilities.Loggers.ILogger;
import Utilities.Loggers.StrategyLogger;
import Utilities.Recorders.ConsoleRecorder;
import Utilities.Recorders.DatedFileStreamRecorder;
import Utilities.Recorders.SingletonCompositeRecorder;
import client.controllers.ICommand;

public class HookLogger implements ICommand {
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
