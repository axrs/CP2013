package client.controllers.recievers;

import client.controllers.ICommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ActionEventStrategy implements EventHandler<ActionEvent> {

    private ICommand command = null;

    public ActionEventStrategy(ICommand command) {
        this.command = command;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        command.execute();
    }
}
