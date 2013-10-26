package DAO;

import Models.User;

public interface IUserDAO {

    public User getCurrentUser();

    public User getUser(int id);

    public void createUser(User user);

    public void removeUser(User user);

    public void removeUser(int id);

    public void updateUser(User user);
}
