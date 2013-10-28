package dao.restDAO.requests.contacts;

import dao.restDAO.requests.Request;

public class RemoveContactRequest extends Request {

    public RemoveContactRequest(int id) {
        setMethod("DELETE");
        setTarget("/api/contacts/" + String.valueOf(id));
    }
}
