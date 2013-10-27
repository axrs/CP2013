package dao.rest.requests.types;

import Models.AppointmentType;
import com.google.gson.Gson;
import dao.rest.requests.Request;

public class CreateTypeRequest extends Request {

    public CreateTypeRequest(AppointmentType type) {
        setMethod("PUT");
        setTarget("/api/types/");
        setMessage(new Gson().toJson(type, AppointmentType.class));
    }
}
