package client.controllers.models;

import Models.AppointmentType;
import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;

public class CreateTypeCommand implements ICommand {

    AppointmentType type = null;
    INotifiable source = null;

    public CreateTypeCommand(AppointmentType c, INotifiable source) {
        this.source = source;
        type = c;
    }

    private boolean isValid() {
        boolean isValid = true;
        if (!type.getDescription().isEmpty() && !type.getDuration().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void execute() {
        if (!isValid()) {
            source.onValidationError("Type must have a description and a duration.");
        } else {
            DAO.getInstance().getTypeDAO().create(type, new ResultListener() {
                @Override
                public void results(Result result) {
                    switch (result.getStatus()) {
                        case 201:
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
}
