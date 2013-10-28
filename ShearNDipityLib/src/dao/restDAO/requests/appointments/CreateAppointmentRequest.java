package dao.restDAO.requests.appointments;

import models.Appointment;
import com.google.gson.Gson;
import dao.restDAO.requests.Request;

public class CreateAppointmentRequest extends Request {

    public CreateAppointmentRequest(Appointment appointment) {
        setMethod("PUT");
        setTarget("/api/appointments/");
        setMessage(new Gson().toJson(appointment, Appointment.class));
    }
}
