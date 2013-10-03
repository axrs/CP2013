package Models;

public class ConsoleLogger extends AbstractLogger {



    public void prepare() {
        //Do nothing for the console
    }

    public void writeMessage() {
        System.out.println(super.getMessage().toString());
    }

    public void finalise() {
        System.out.println("\n");
    }
}
