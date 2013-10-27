package dao.rest.requests.providers;

import dao.rest.requests.Request;

public class GetProviderRequest extends Request {

    public GetProviderRequest(int id) {
        setMethod("GET");
        setTarget("/api/providers/" + String.valueOf(id));
    }
}
