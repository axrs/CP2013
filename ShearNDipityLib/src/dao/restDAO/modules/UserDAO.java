package dao.restDAO.modules;

import models.User;
import com.google.gson.Gson;
import dao.IUserDAO;
import dao.events.UpdatedEvent;
import dao.events.UserUpdatedListener;
import dao.restDAO.client.ActiveRESTClient;
import dao.restDAO.Publisher;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import dao.restDAO.requests.Request;
import dao.restDAO.requests.users.CreateUserRequest;
import dao.restDAO.requests.users.GetCurrentUserRequest;
import dao.restDAO.requests.users.LoginUserRequest;


public class UserDAO extends Publisher implements IUserDAO {

    private static User currentUser = null;
    private static UserDAO instance = null;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public static User getCurrentUser() {
        return getInstance().getUser();
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public void setUser(String token) {
        Request.setToken(token);
        Request r = new GetCurrentUserRequest();
        r.addResultListener(onLoginResult());
        ActiveRESTClient.addRequest(r);
    }

    public void setUser(User u) {
        currentUser = u;
        Request.setToken(u.getToken());
        fireUpdated(new UpdatedEvent(this));
    }

    @Override
    public void login(User user, ResultListener listener) {
        Request r = new LoginUserRequest(user);
        if (listener != null) {
            r.addResultListener(listener);
        }
        r.addResultListener(onLoginResult());
        ActiveRESTClient.addRequest(r);
    }

    private ResultListener onLoginResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {
                User user;
                if (result.getStatus() != 202) return;

                user = new Gson().fromJson(result.getResponse(), User.class);
                if (user.getContactId() != 0) {
                    setUser(user);
                }
            }
        };
    }

    private void fireUpdated(UpdatedEvent event) {
        UserUpdatedListener[] listeners = subscribers.getListeners(UserUpdatedListener.class);
        for (UserUpdatedListener listener : listeners) {
            listener.updated(event);
        }
    }

    @Override
    public void login(User user) {
        login(user, null);
    }

    @Override
    public void create(User user, ResultListener listener) {
        Request r = new CreateUserRequest(user);
        if (listener != null) {
            r.addResultListener(listener);
        }
        ActiveRESTClient.addRequest(r);
    }

    @Override
    public void create(User user) {
        create(user, null);
    }

    @Override
    public void update(User user, ResultListener listener) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void remove(User user, ResultListener listener) {

    }

    @Override
    public void remove(User user) {

    }

    @Override
    public void remove(int id, ResultListener listener) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void addUpdatedEventLister(UserUpdatedListener listener) {
        subscribers.add(UserUpdatedListener.class, listener);
    }

    @Override
    public void removeUpdatedEventListener(UserUpdatedListener listener) {
        subscribers.remove(UserUpdatedListener.class, listener);
    }
}
