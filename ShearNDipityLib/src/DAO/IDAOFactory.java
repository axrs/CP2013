package DAO;

public interface IDAOFactory {
    public IContactDAO getContactDAO();

    public IProviderDAO getProviderDAO();

    public IUserDAO getUserDAO();
}
