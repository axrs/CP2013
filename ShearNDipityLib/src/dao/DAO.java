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

    public IContactDAO getContactDAO() {
        return getInstance().getContactsFactoryDAO();
    }
}
