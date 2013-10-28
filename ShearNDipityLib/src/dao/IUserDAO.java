package dao;

import Models.User;
import dao.events.UserUpdatedListener;
import dao.restDAO.listeners.ResultListener;

public interface IUserDAO {
    public User getUser();

    public void setUser(String token);

    public void setUser(User u);

    public void login(User user, ResultListener listener);

    public void login(User user);

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
