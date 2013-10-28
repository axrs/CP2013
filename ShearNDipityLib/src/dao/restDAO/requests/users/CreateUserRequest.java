package dao.restDAO.requests.users;

import models.User;
import com.google.gson.Gson;
import dao.restDAO.requests.Request;

public class CreateUserRequest extends Request {

    public CreateUserRequest(User user) {
        setMethod("PUT");
        setTarget("/api/users/");
        setMessage(new Gson().toJson(user, User.class));
    }
}
