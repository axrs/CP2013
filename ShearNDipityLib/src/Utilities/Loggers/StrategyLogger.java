package Utilities.Loggers;

import Utilities.Loggers.FormatStrategies.IFormatStrategy;
import Utilities.Recorders.IRecorder;

public class StrategyLogger implements ILogger {
    IRecorder recorder = null;
    IFormatStrategy strategy = null;

    public StrategyLogger(IRecorder recorder, IFormatStrategy strategy) {
        this.recorder = recorder;
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
