package dao.rest.requests.contacts;

import dao.rest.requests.Request;

public class GetContactRequest extends Request {

    public GetContactRequest(int id) {
        setMethod("GET");
        setTarget("/api/contacts/" + String.valueOf(id));
    }
}
