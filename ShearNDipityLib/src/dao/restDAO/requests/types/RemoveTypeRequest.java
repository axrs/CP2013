package dao.restDAO.requests.types;

import dao.restDAO.requests.Request;

public class RemoveTypeRequest extends Request {

    public RemoveTypeRequest(int id) {
        setMethod("DELETE");
        setTarget("/api/types/" + String.valueOf(id));
    }
}
