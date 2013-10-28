package utilities.loggers;

import utilities.loggers.strategies.IFormatStrategy;
import utilities.loggers.strategies.TimeFormatStrategy;
import utilities.recorders.IRecorder;

public class StrategyLogger extends BasicLogger {
    IFormatStrategy strategy = new TimeFormatStrategy();

    public StrategyLogger(IRecorder recorder) {
        super(recorder);
    }

    public StrategyLogger(IRecorder recorder, IFormatStrategy strategy) {
        super(recorder);
        setStrategy(strategy);
    }

    public void setStrategy(IFormatStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void log(String message) {
        recorder.record(strategy.format(message));
    }
}
