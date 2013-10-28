package dao.restDAO.requests.users;

import models.User;
import com.google.gson.Gson;
import dao.restDAO.requests.Request;

public class LoginUserRequest extends Request {

    public LoginUserRequest(User user) {
        setMethod("PUT");
        setTarget("/api/user/login");
        setMessage(new Gson().toJson(user, User.class));
    }
}
