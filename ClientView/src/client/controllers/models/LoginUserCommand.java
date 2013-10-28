package client.controllers.models;

import models.User;
import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;

public class LoginUserCommand implements ICommand {

    User user = null;
    INotifiable source = null;

    public LoginUserCommand(User user, INotifiable source) {
        this.source = source;
        this.user = user;
    }

    public LoginUserCommand(String userName, String password, INotifiable source) {
        this.source = source;
        User u = new User();
        u.setUserName(userName);
        u.setPassword(password);
        this.user = u;
    }

    @Override
    public void execute() {

        DAO.getInstance().getUserDAO().login(user, new ResultListener() {
            @Override
            public void results(Result result) {
                if (source == null) return;
                switch (result.getStatus()) {
                    case 202:
                        source.onSuccess();
                        break;
                    case 404://Unknown User
                        source.onError("Invalid User Credentials.");
                        break;
                    case 409://Bad Password
                        source.onError("Invalid User Credentials.");
                        break;
                    case 500:
                        source.onError("Database Error");
                        break;
                }
            }
        });

    }
}
