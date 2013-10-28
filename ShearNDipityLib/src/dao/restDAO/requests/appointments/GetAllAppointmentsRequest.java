package dao.restDAO.requests.appointments;

import dao.restDAO.requests.Request;

public class GetAllAppointmentsRequest extends Request {

    public GetAllAppointmentsRequest() {
        setMethod("GET");
        setTarget("/api/appointments/");
    }
}
