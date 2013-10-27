package client.controllers.models;

import Models.User;
import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;

public class LoginUserCommand implements ICommand {

    User user = null;
    INotifiable source = null;

    public LoginUserCommand(User user, INotifiable source) {
        this.source = source;
        this.user = user;
    }

    @Override
    public void execute() {

        DAO.getInstance().getUserDAO().create(user, new ResultListener() {
            @Override
            public void results(Result result) {
                if (result.getStatus() == 201) {

                }
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
