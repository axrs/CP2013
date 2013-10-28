package dao.restDAO;

import dao.*;
import dao.restDAO.modules.*;

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

    @Override
    public IAvailabilitiesDAO getAvailabilitiesDAO() {
        return AvailabilityDAO.getInstance();
    }

    @Override
    public IAppointmentDAO getAppointmentDAO() {
        return AppointmentDAO.getInstance();
    }
}
