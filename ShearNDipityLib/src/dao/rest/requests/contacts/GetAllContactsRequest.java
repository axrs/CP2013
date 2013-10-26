package dao.rest.requests.contacts;

import dao.rest.requests.Request;

public class GetAllContactsRequest extends Request {

    public GetAllContactsRequest() {
        setMethod("GET");
        setTarget("/api/contacts");
    }
}
