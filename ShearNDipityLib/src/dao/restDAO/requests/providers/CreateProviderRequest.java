package dao.restDAO.requests.providers;

import models.ServiceProvider;
import com.google.gson.Gson;
import dao.restDAO.requests.Request;

public class CreateProviderRequest extends Request {

    public CreateProviderRequest(ServiceProvider provider) {
        setMethod("PUT");
        setTarget("/api/providers/");
        setMessage(new Gson().toJson(provider, ServiceProvider.class));
    }
}
