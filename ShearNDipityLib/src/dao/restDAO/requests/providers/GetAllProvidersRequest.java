package dao.restDAO.requests.providers;

import dao.restDAO.requests.Request;

public class GetAllProvidersRequest extends Request {

    public GetAllProvidersRequest() {
        setMethod("GET");
        setTarget("/api/providers/");
    }
}


