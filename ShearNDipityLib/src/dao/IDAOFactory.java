package dao;

public interface IDAOFactory {
    public IContactDAO getContactDAO();

    public IProviderDAO getProviderDAO();

    public IUserDAO getUserDAO();

    public ITypeDAO getTypeDAO();

    public IAvailabilitiesDAO getAvailabilitiesDAO();

    public IAppointmentDAO getAppointmentDAO();
}
