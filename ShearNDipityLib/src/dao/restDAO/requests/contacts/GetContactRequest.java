package dao.restDAO.requests.contacts;

import dao.restDAO.requests.Request;

public class GetContactRequest extends Request {

    public GetContactRequest(int id) {
        setMethod("GET");
        setTarget("/api/contacts/" + String.valueOf(id));
    }
}
