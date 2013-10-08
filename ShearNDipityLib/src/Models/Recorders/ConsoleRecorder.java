package Models.Recorders;

import Interfaces.IRecorder;

public class ConsoleRecorder implements IRecorder {
    @Override
    public void record(String message) {
        System.out.println(message);
    }
}
