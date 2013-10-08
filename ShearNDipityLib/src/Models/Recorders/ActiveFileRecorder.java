package Models.Recorders;

import Interfaces.IRecorder;

import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
The Active Object pattern, which decouples method execution from method invocation in order to simplify synchronized
access to a shared resource by methods invoked in different threads of control. The Active Object pattern allows one or
more independent threads of execution to interleave their access to data modeled as a single object.

This pattern is commonly used in distributed systems requiring multi-threaded servers. In addition, client applications
(such as windowing systems and network browsers), are increasingly employing active objects to simplify concurrent,
asynchronous network operations.

NOTE: Introduction of Getters/Setters may create a race condition and hence require implementation for synchronised
      access.  This additional implementation would break the Active Object Pattern.
 */

public class ActiveFileRecorder implements IRecorder {

    private BlockingQueue<String> recordQueue = new LinkedBlockingQueue<String>();

    public ActiveFileRecorder() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        FileOutputStream stream = new FileOutputStream(_filePath, true);
                        _stream.write(message.getBytes());
                        while (true) {
                            //Try to take from the queue
                            //Log to the filestream


                        }
                        //Cleanup the file
                    }
                }
        ).start();

    }

    @Override
    public void record(String message) {
        try {
            recordQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
