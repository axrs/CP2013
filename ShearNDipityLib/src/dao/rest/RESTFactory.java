package dao.rest;

import dao.*;

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

    @Override
    public ITypeDAO getTypeDAO() {
        return TypeDAO.getInstance();
    }
}
