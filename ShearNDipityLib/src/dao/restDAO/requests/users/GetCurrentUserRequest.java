package dao.restDAO.requests.users;

import dao.restDAO.requests.Request;

public class GetCurrentUserRequest extends Request {

    public GetCurrentUserRequest() {
        setMethod("GET");
        setTarget("/api/user/");
    }
}
