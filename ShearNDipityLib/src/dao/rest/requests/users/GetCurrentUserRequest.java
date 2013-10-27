package dao.rest.requests.users;

import dao.rest.requests.Request;

public class GetCurrentUserRequest extends Request {

    public GetCurrentUserRequest() {
        setMethod("GET");
        setTarget("/api/user/");
    }
}
