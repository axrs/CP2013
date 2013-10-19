package client.controllers.recievers;

import client.controllers.ShowAboutWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ShowAboutWindowAction implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        new ShowAboutWindow().execute();
    }
}
