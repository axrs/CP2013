package client.controllers.models;

import Models.ServiceProvider;
import client.controllers.ICommand;
import client.scene.INotifiable;
import dao.DAO;
import dao.rest.events.Result;
import dao.rest.listeners.ResultListener;

public class UpdateProviderCommand implements ICommand {

    ServiceProvider provider = null;
    INotifiable source = null;

    public UpdateProviderCommand(ServiceProvider c, INotifiable source) {
        this.source = source;
        provider = c;
    }

    private boolean isValid() {
        boolean isValid = true;
        if (provider.getName().isEmpty() || provider.getSurname().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void execute() {
        if (!isValid()) {
            System.out.println("HERE");
            source.onValidationError("Provider must have a name and surname.");
        } else {
            System.out.println("update");
            DAO.getInstance().getProviderDAO().update(provider, new ResultListener() {
                @Override
                public void results(Result result) {
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
}