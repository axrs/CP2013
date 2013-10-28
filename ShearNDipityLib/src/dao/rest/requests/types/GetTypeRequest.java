package dao.rest.requests.types;

import dao.rest.requests.Request;

public class GetTypeRequest extends Request {

    public GetTypeRequest(int id) {
        setMethod("GET");
        setTarget("/api/types/" + String.valueOf(id));
    }
}
