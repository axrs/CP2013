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
        return ProviderDAO.getInstance();
    }

    @Override
    public IUserDAO getUserDAO() {
        return UserDAO.getInstance();
    }
}
