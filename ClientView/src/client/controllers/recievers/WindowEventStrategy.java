package client.controllers.recievers;

import client.controllers.ICommand;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class WindowEventStrategy implements EventHandler<WindowEvent> {

    private ICommand command = null;

    public WindowEventStrategy(ICommand command) {
        this.command = command;
    }

    public static WindowEventStrategy create(ICommand command) {
        return new WindowEventStrategy(command);
    }

    @Override
    public void handle(WindowEvent event) {
        command.execute();
    }
}