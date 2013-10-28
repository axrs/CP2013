package dao.restDAO.requests.contacts;

import dao.restDAO.requests.Request;

public class GetAllContactsRequest extends Request {

    public GetAllContactsRequest() {
        setMethod("GET");
        setTarget("/api/contacts");
    }
}
