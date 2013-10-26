package dao;

import Models.User;

public interface IUserDAO {

    public void getCurrentUser();

    public void getUser(int id);

    public void createUser(User user);

    public void removeUser(User user);

    public void removeUser(int id);

    public void updateUser(User user);
}
