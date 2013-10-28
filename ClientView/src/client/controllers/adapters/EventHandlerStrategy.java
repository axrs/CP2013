package client.controllers.adapters;

import client.controllers.ICommand;
import javafx.event.Event;
import javafx.event.EventHandler;


public class EventHandlerStrategy implements EventHandler {

    private ICommand command = null;

    public EventHandlerStrategy(ICommand command) {
        this.command = command;
    }

    public static ActionEventStrategy create(ICommand command) {
        return new ActionEventStrategy(command);
    }

    @Override
    public void handle(Event event) {
        command.execute();
    }
}
