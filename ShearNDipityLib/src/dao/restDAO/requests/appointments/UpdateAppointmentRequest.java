package dao.restDAO.requests.appointments;

import models.Appointment;
import com.google.gson.Gson;
import dao.restDAO.requests.Request;

public class UpdateAppointmentRequest extends Request {

    public UpdateAppointmentRequest(Appointment appointment) {
        setMethod("PUT");
        setTarget("/api/appointments/" + String.valueOf(appointment.getAppointmentId()));
        setMessage(new Gson().toJson(appointment, Appointment.class));
    }

}
