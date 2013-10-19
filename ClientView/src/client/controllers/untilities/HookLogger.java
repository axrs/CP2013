package client.controllers.untilities;

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


/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 19/10/13
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class HookLogger implements ICommand{
    final StrategyLogger timeStampedLogger = new StrategyLogger(new ConsoleRecorder(), new TimeFormatStrategy());


    @Override
    public void execute() {
        {
            SingletonCompositeRecorder scr = SingletonCompositeRecorder.getInstance();
            scr.add(new DatedFileStreamRecorder("./logs"));
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
