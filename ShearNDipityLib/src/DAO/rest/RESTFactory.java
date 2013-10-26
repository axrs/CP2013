package DAO.rest;

import DAO.IContactDAO;
import DAO.IDAOFactory;
import DAO.IProviderDAO;
import DAO.IUserDAO;

public class RESTFactory implements IDAOFactory {

    @Override
    public IContactDAO getContactDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IProviderDAO getProviderDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IUserDAO getUserDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
