package dao.rest.requests;

import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;

import javax.swing.event.EventListenerList;

public class Request {
    private static String token = "";
    private static String location = "";
    protected String target = "";
    protected String message = "";
    protected String method = "GET";
    protected EventListenerList subscribers = new EventListenerList();

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Request.token = token;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Request.location = location;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void addResultListener(ResultListener listener) {
        subscribers.add(ResultListener.class, listener);
    }

    public void resultReturned(Result restRequest) {
        triggerResults(restRequest);
    }

    private void triggerResults(Result result) {
        Object[] listeners = subscribers.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == ResultListener.class) {
                ((ResultListener) listeners[i + 1]).results(result);
            }
        }
    }
}
