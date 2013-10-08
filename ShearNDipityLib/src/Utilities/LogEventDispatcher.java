package Utilities;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogEventDispatcher {

    private static LogEventDispatcher _instance = null;
    protected BlockingQueue<ILogListener> subscribers = new LinkedBlockingQueue<ILogListener>();
    private BlockingQueue<String> dispatchQueue
            = new LinkedBlockingQueue<String>();

    protected LogEventDispatcher() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (dispatchQueue.size() > 0) {
                                try {
                                    String message = dispatchQueue.take();
                                    for (ILogListener listener : subscribers) {
                                        listener.onLog(message);
                                    }
                                } catch (InterruptedException e) {
                                }
                            }
                        }
                    }
                }
        ).start();
    }

    public static void log(String message) {
        getInstance().addLogMessage(message);
    }

    public static void addListener(ILogListener listener) {
        getInstance().subscribe(listener);
    }

    public static void removeListener(ILogListener listener) {
        getInstance().unsubscribe(listener);
    }

    private static LogEventDispatcher getInstance() {
        if (_instance == null) {
            _instance = new LogEventDispatcher();
        }
        return _instance;
    }

    private void unsubscribe(ILogListener listener) {
        subscribers.remove(listener);
    }

    private void subscribe(ILogListener listener) {
        try {
            subscribers.put(listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addLogMessage(String message) {
        try {
            dispatchQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
