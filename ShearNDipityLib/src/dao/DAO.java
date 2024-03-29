package dao;

import dao.restDAO.RESTFactory;

public class DAO implements IDAOFactory {
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

    private ITypeDAO getTypeFactoryDAO() {
        return factory.getTypeDAO();
    }

    private IAvailabilitiesDAO getAvailabilitiesFactoryDAO() {
        return factory.getAvailabilitiesDAO();
    }

    private IAppointmentDAO getAppointmentFactoryDAO() {
        return factory.getAppointmentDAO();
    }

    public ITypeDAO getTypeDAO() {
        return getInstance().getTypeFactoryDAO();
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

    public IAvailabilitiesDAO getAvailabilitiesDAO() {
        return getInstance().getAvailabilitiesFactoryDAO();
    }

    public IAppointmentDAO getAppointmentDAO() {
        return getInstance().getAppointmentFactoryDAO();
    }
}
