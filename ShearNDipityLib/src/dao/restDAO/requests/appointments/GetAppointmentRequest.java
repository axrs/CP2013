package dao.restDAO.requests.appointments;

import dao.restDAO.requests.Request;

public class GetAppointmentRequest extends Request {

    public GetAppointmentRequest(int id) {
        setMethod("GET");
        setTarget("/api/appointments/" + String.valueOf(id));
    }
}
