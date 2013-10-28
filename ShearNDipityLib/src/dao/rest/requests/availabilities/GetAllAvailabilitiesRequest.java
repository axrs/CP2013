package dao.rest.requests.availabilities;

import dao.rest.requests.Request;

public class GetAllAvailabilitiesRequest extends Request {

    public GetAllAvailabilitiesRequest(String startDate, String endDate) {
        setMethod("GET");
        setTarget("/api/available/" + startDate + "/" + endDate);
    }
}
