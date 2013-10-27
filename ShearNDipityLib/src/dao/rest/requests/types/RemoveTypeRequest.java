package dao.rest.requests.types;

import dao.rest.requests.Request;

public class RemoveTypeRequest extends Request {

    public RemoveTypeRequest(int id) {
        setMethod("DELETE");
        setTarget("/api/types/" + String.valueOf(id));
    }
}
