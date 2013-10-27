package dao.rest.requests.types;

import Models.AppointmentType;
import com.google.gson.Gson;
import dao.rest.requests.Request;

public class UpdateTypeRequest extends Request {

    public UpdateTypeRequest(AppointmentType type) {
        setMethod("PUT");
        setTarget("/api/types/" + type.getTypeId());
        setMessage(new Gson().toJson(type, AppointmentType.class));
    }
}
