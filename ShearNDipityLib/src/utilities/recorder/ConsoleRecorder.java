package utilities.recorders;

public class ConsoleRecorder implements IRecorder {

    @Override
    public void record(String message) {
        System.out.println(message);
    }
}
