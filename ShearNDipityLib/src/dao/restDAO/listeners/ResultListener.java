package dao.restDAO.listeners;

import dao.restDAO.events.Result;

import java.util.EventListener;

public interface ResultListener extends EventListener {
    public void results(Result result);
}
