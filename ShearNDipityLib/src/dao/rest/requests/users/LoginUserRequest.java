package dao.rest.requests.users;

import Models.User;
import com.google.gson.Gson;
import dao.rest.requests.Request;

public class LoginUserRequest extends Request {

    public LoginUserRequest(User user) {
        setMethod("PUT");
        setTarget("/api/user/login");
        setMessage(new Gson().toJson(user, User.class));
    }
}