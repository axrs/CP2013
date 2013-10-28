package dao.restDAO.requests.appointments;

import dao.restDAO.requests.Request;

public class RemoveAppointmentRequest extends Request {

    public RemoveAppointmentRequest(int id) {
        setMethod("DELETE");
        setTarget("/api/appointments/" + String.valueOf(id));
    }
}
