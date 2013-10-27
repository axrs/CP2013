package dao.rest.requests.providers;

import Models.ServiceProvider;
import com.google.gson.Gson;
import dao.rest.requests.Request;

public class CreateProviderRequest extends Request {

    public CreateProviderRequest(ServiceProvider provider) {
        setMethod("PUT");
        setTarget("/api/providers/");
        setMessage(new Gson().toJson(provider, ServiceProvider.class));
    }
}
