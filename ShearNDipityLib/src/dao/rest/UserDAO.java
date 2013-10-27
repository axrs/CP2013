package dao.rest;

import Models.User;
import dao.IUserDAO;
import dao.events.UserUpdatedListener;
import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;
import dao.rest.requests.Request;
import dao.rest.requests.users.CreateUserRequest;
import dao.rest.requests.users.GetCurrentUserRequest;


public class UserDAO implements IUserDAO {

    private static User currentUser = null;
    private static UserDAO instance = null;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public void getUser() {
        Request r = new GetCurrentUserRequest();
    }

    @Override
    public void setUser() {

    }

    private ResultListener onGetCurrentUserResult() {
        return new ResultListener() {
            @Override
            public void results(Result result) {

            }
        };
    }

    public void setUser(User u) {
        currentUser = u;
        Request.setToken(u.getToken());
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

    }

    @Override
    public void removeUpdatedEventListener(UserUpdatedListener listener) {

    }
}
