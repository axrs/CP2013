package dao.rest.listeners;

import dao.rest.events.Result;

import java.util.EventListener;

public interface ResultListener extends EventListener {
    public void results(Result result);
}
