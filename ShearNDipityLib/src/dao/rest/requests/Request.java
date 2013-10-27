package dao.rest.requests;

import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Request {
    private static String token = "0";
    private static String location = "";
    protected String target = "";
    protected String message = "";
    protected String method = "GET";
    List<ResultListener> resultListeners = Collections.synchronizedList(new ArrayList<ResultListener>());

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
        resultListeners.add(listener);
    }

    public void resultReturned(Result restRequest) {
        triggerResults(restRequest);
    }

    private void triggerResults(Result result) {
        for (ResultListener l : resultListeners) {
            l.results(result);
        }
    }
}
