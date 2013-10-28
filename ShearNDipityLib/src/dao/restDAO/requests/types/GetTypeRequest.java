package dao.restDAO.requests.types;

import dao.restDAO.requests.Request;

public class GetTypeRequest extends Request {

    public GetTypeRequest(int id) {
        setMethod("GET");
        setTarget("/api/types/" + String.valueOf(id));
    }
}
