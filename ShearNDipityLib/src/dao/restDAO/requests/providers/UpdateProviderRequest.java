package dao.restDAO.requests.providers;

import Models.ServiceProvider;
import com.google.gson.Gson;
import dao.restDAO.requests.Request;

public class UpdateProviderRequest extends Request {

    public UpdateProviderRequest(ServiceProvider provider) {
        setMethod("PUT");
        setTarget("/api/providers/" + String.valueOf(provider.getProviderId()));
        setMessage(new Gson().toJson(provider, ServiceProvider.class));
    }
}
