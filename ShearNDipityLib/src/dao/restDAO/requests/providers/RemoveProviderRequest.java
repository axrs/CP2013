package dao.restDAO.requests.providers;

import dao.restDAO.requests.Request;

public class RemoveProviderRequest extends Request {

    public RemoveProviderRequest(int id) {
        setMethod("DELETE");
        setTarget("/api/providers/" + String.valueOf(id));
    }
}
