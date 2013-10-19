package Controllers;

import Interfaces.Command;
import Utilities.LogEventDispatcher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RESTService {
    private BlockingQueue<Command> commandQueue =
            new LinkedBlockingQueue<Command>();

    public RESTService() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true)
                            if (commandQueue.size() > 0) {
                                try {
                                    LogEventDispatcher.log("Attempting RESTService Request");
                                    commandQueue.take().execute();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                    }
                }
        ).start();
    }

    public void add(Command command) throws InterruptedException {
        commandQueue.put(command);
    }
}
