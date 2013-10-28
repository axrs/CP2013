package client.controllers.models;

import Models.User;
import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;

public class CreateUserCommand implements ICommand {

    User user = null;
    INotifiable source = null;

    public CreateUserCommand(User user, INotifiable source) {
        this.source = source;
        this.user = user;
    }

    private boolean isValid() {
        boolean isValid = true;
        if (user.getName().isEmpty() || user.getSurname().isEmpty()
                || user.getPassword().isEmpty() || user.getUserName().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void execute() {
        if (!isValid()) {
            if (source != null) {
                source.onValidationError("Users must have a name, surname, user name and password.");
            }
        } else {
            DAO.getInstance().getUserDAO().create(user, new ResultListener() {
                @Override
                public void results(Result result) {
                    if (source == null) return;
                    switch (result.getStatus()) {
                        case 201:
                            source.onSuccess();
                            break;
                        case 400:
                            source.onError("Bad request to server.");
                            break;
                        case 409:
                            source.onError("User with specified user name already exists.");
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
