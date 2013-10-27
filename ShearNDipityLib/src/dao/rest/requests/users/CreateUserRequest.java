package dao.rest.requests.users;

import Models.User;
import com.google.gson.Gson;
import dao.rest.requests.Request;

public class CreateUserRequest extends Request {

    public CreateUserRequest(User user) {
        setMethod("PUT");
        setTarget("/api/users/");
        setMessage(new Gson().toJson(user, User.class));
    }
}
