package dao.restDAO.requests.availabilities;

import dao.restDAO.requests.Request;

public class GetAllAvailabilitiesRequest extends Request {

    public GetAllAvailabilitiesRequest(String startDate, String endDate) {
        setMethod("GET");
        setTarget("/api/available/" + startDate + "/" + endDate);
    }
}
