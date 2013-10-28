package client.controllers.models;

import Models.AppointmentType;
import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;

public class RemoveTypeCommand implements ICommand {

    AppointmentType type = null;
    INotifiable source = null;

    public RemoveTypeCommand(AppointmentType c, INotifiable source) {
        this.source = source;
        type = c;
    }

    @Override
    public void execute() {
        DAO.getInstance().getTypeDAO().remove(type.getTypeId(), new ResultListener() {
            @Override
            public void results(Result result) {
                switch (result.getStatus()) {
                    case 202:
                        source.onSuccess();
                        break;
                    case 400:
                        source.onError("Bad request to server.");
                        break;
                    case 500:
                        source.onError("Database Error");
                        break;
                }
            }
        });
    }
}
