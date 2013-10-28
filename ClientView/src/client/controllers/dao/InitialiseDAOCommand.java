package client.controllers.dao;

import client.controllers.ICommand;
import dao.DAO;

public class InitialiseDAOCommand implements ICommand {

    @Override
    public void execute() {
        DAO.getInstance().getTypeDAO();
        DAO.getInstance().getProviderDAO();
        DAO.getInstance().getContactDAO();
        DAO.getInstance().getUserDAO();
        DAO.getInstance().getAvailabilitiesDAO();
    }
}
