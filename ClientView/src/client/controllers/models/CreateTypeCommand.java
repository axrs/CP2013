package client.controllers.models;

import Models.AppointmentType;
import Models.Contact;
import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;

public class CreateTypeCommand implements ICommand {

    AppointmentType contact = null;
    INotifiable source = null;

    public CreateTypeCommand(AppointmentType c, INotifiable source) {
        this.source = source;
        contact = c;
    }

    private boolean isValid() {
        boolean isValid = true;
        if (!contact.getDescription().isEmpty() && !contact.getDuration().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void execute() {
        if (!isValid()) {
            source.onValidationError("Contact must have a name and surname.");
        } else {
            DAO.getInstance().getContactDAO().create(contact, new ResultListener() {
                @Override
                public void results(Result result) {
                    System.out.println(result.getStatus());
                    switch (result.getStatus()) {
                        case 201:
                            source.onSuccess();
                            break;
                        case 400:
                            source.onError("Bad request to server.");
                            break;
                        case 409:
                            source.onError("Contact with specified name already exists.");
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