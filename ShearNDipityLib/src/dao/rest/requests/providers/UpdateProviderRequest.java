package dao.rest.requests.providers;

import Models.ServiceProvider;
import com.google.gson.Gson;
import dao.rest.requests.Request;

public class UpdateProviderRequest extends Request {

    public UpdateProviderRequest(ServiceProvider provider) {
        setMethod("PUT");
        setTarget("/api/provider/" + String.valueOf(provider.getProviderId()));
        setMessage(new Gson().toJson(provider, ServiceProvider.class));
    }
}
