/**
 * RESTRunner
 *
 * Threaded REST request object for all purposes
 *
 * Created by xander on 8/22/13.
 */

package Controllers;

import javax.swing.event.EventListenerList;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EventListener;
import java.util.EventObject;


public class RESTRunner implements Runnable {
    protected String target = "";
    protected String message = "";
    protected String method = "GET";
    protected EventListenerList subscribers = new EventListenerList();

    /**
     * Reads Input Stream to String
     *
     * @param stream
     * @return
     * @throws IOException
     */
    private static String ReadStream(InputStream stream) throws IOException {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[4096];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            result = buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
        return result;
    }

    /**
     * Returns the current REST request method
     *
     * @return request method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the REST request method
     *
     * @param method request method (GET, PUT, DELETE)
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the current URL target
     *
     * @return URL
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the URL target
     *
     * @param target URL
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Gets the message to send to the server
     *
     * @return JSON data
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message to send to the server
     *
     * @param message JSON data
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets REST request to GET url
     *
     * @param url
     */
    public void setRequest(String url) {
        target = url;
        this.method = "GET";
        this.message = "";
    }

    /**
     * Sets REST request to method at url
     *
     * @param url
     * @param method
     */
    public void setRequest(String url, String method) {
        target = url;
        this.method = method;
        this.message = "";
    }

    /**
     * Sets REST request
     *
     * @param url
     * @param method
     * @param message
     */
    public void setRequest(String url, String method, String message) {
        target = url;
        this.method = method;
        this.message = message;
    }

    /**
     * Runnable execution cycle
     */
    public void run() {
        //Create an empty result object to return if something goes wrong
        Result result = new Result(this);

        //Attempt connection to the server
        try {
            if (!target.isEmpty()) {
                URL url = new URL(target);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //Configure the HTTP connection
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod(method);

                //Set request types
                connection.setRequestProperty("Content-Type", "application/json");  //must be json
                connection.setRequestProperty("Accept", "application/json");         //must be json

                //Write message to server over socket
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(message);
                writer.flush();
                writer.close();

                //Capture the server status code and response
                result = new Result(this, connection.getResponseCode(), ReadStream(connection.getInputStream()));

                //disconnect from the server
                connection.disconnect();
            }

        } catch (Exception ex) {
            //throw new RuntimeException(ex);
        }

        //Fire results to any listeners
        triggerResults(result);
    }

    /**
     * Add a response subscriber
     *
     * @param listener
     */
    public void addListner(ResultsListener listener) {
        subscribers.add(ResultsListener.class, listener);
    }

    /**
     * Remove a response subscriber
     *
     * @param listener
     */
    public void removeListener(ResultsListener listener) {
        subscribers.remove(ResultsListener.class, listener);
    }

    /**
     * Trigger a server response
     *
     * @param result
     */
    private void triggerResults(Result result) {

        Object[] listeners = subscribers.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            //Each listener has two components. The listener and the listener instance.
            if (listeners[i] == ResultsListener.class) {
                ((ResultsListener) listeners[i + 1]).results(result);
            }
        }
    }

    /**
     * REST Server Result Listener Interface
     */
    public interface ResultsListener extends EventListener {
        public void results(Result result);
    }

    /**
     * REST Server Result Object
     */
    public class Result extends EventObject {
        private int status = 0;
        private String response = "";

        public Result(Object source) {
            super(source);
        }

        public Result(Object source, int status, String response) {
            super(source);
            this.status = status;
            this.response = response;
        }

        public int getStatus() {
            return status;
        }

        public String getResponse() {
            return response;
        }

    }
}
