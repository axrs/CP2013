package dao.restDAO.requests.providers;

import dao.restDAO.requests.Request;

public class GetProviderRequest extends Request {

    public GetProviderRequest(int id) {
        setMethod("GET");
        setTarget("/api/providers/" + String.valueOf(id));
    }
}
