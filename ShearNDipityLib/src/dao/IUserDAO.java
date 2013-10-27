package dao;

import Models.User;
import dao.events.UserUpdatedListener;
import dao.rest.listeners.ResultListener;

public interface IUserDAO {
    public User getCurrentUser();

    public void getUser();

    public void setUser();

    public void create(User user, ResultListener listener);

    public void create(User user);

    public void update(User user, ResultListener listener);

    public void update(User user);

    public void remove(User user, ResultListener listener);

    public void remove(User user);

    public void remove(int id, ResultListener listener);

    public void remove(int id);

    public void addUpdatedEventLister(UserUpdatedListener listener);

    public void removeUpdatedEventListener(UserUpdatedListener listener);
}
