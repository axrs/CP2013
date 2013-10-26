package Controllers;

import Interfaces.BaseController;
import javafx.event.EventHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserController implements BaseController, ActionListener, EventHandler<javafx.event.ActionEvent> {
    @Override
    public void actionPerformed(ActionEvent e) {
        validateModelInformation();
    }

    @Override
    public void updateView() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateModel() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void handle(javafx.event.ActionEvent actionEvent) {
        validateModelInformation();
    }
}
