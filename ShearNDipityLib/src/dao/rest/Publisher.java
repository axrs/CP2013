package dao.rest;

import javax.swing.event.EventListenerList;

public abstract class Publisher extends Locker {
    protected EventListenerList subscribers = new EventListenerList();
}
