package dao.rest;

import dao.IContactDAO;
import dao.IDAOFactory;
import dao.IProviderDAO;
import dao.IUserDAO;

public class RESTFactory implements IDAOFactory {

    @Override
    public IContactDAO getContactDAO() {
        return ContactDAO.getInstance();
    }

    @Override
    public IProviderDAO getProviderDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IUserDAO getUserDAO() {
        return UserDAO.getInstance();
    }
}
