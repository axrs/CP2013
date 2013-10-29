package client.controllers.models;

import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.restDAO.events.Result;
import dao.restDAO.listeners.ResultListener;
import models.ServiceProvider;

public class RemoveProviderCommand implements ICommand {

    ServiceProvider provider = null;
    INotifiable source = null;

    public RemoveProviderCommand(ServiceProvider c, INotifiable source) {
        this.source = source;
        provider = c;
    }

    @Override
    public void execute() {
        DAO.getInstance().getProviderDAO().remove(provider, new ResultListener() {
            @Override
            public void results(Result result) {
                if (source == null) return;
                switch (result.getStatus()) {
                    case 202:
                        source.onSuccess();
                        break;
                    case 400:
                        source.onError("Bad request to server.");
                        break;
                    case 409:
                        source.onError("Contact with specified name already exists.");
                        break;
                    case 500:
                        source.onError("Database Error");
                        break;
                }
            }
        });

    }
}
