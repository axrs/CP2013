package dao.rest.requests.contacts;

import dao.rest.requests.Request;

public class RemoveContactRequest extends Request {

    public RemoveContactRequest(int id) {
        setMethod("DELETE");
        setTarget("/api/contacts/" + String.valueOf(id));
    }
}
