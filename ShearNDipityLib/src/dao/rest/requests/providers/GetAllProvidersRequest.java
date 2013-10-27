package dao.rest.requests.providers;

import dao.rest.requests.Request;

public class GetAllProvidersRequest extends Request {

    public GetAllProvidersRequest() {
        setMethod("GET");
        setTarget("/api/providers");
    }
}


