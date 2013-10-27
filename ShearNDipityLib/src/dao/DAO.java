package dao;

import dao.rest.RESTFactory;

public class DAO {
    private static DAO instance;
    private IDAOFactory factory;

    protected DAO() {
        factory = new RESTFactory();
    }

    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    private IContactDAO getContactsFactoryDAO() {
        return factory.getContactDAO();
    }

    private IUserDAO getUserFactoryDAO() {
        return factory.getUserDAO();
    }

    private IProviderDAO getProviderFactoryDAO() {
        return factory.getProviderDAO();
    }

    public IContactDAO getContactDAO() {
        return getInstance().getContactsFactoryDAO();
    }

    public IUserDAO getUserDAO() {
        return getInstance().getUserFactoryDAO();
    }

    public IProviderDAO getProviderDAO() {
        return getInstance().getProviderFactoryDAO();
    }
}
