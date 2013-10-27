package dao.rest.requests.providers;

import dao.rest.requests.Request;

public class RemoveProviderRequest extends Request {

    public RemoveProviderRequest(int id) {
        setMethod("DELETE");
        setTarget("/api/providers/" + String.valueOf(id));
    }
}
