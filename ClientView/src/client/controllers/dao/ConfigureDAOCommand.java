package client.controllers.dao;

import models.Config;
import client.controllers.ICommand;
import dao.restDAO.requests.Request;

public class ConfigureDAOCommand implements ICommand {
    @Override
    public void execute() {
        Request.setLocation(Config.getInstance().getServer());
    }
}
