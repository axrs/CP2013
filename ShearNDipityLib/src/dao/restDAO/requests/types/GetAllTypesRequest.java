package dao.restDAO.requests.types;

import dao.restDAO.requests.Request;

public class GetAllTypesRequest extends Request {

    public GetAllTypesRequest() {
        setMethod("GET");
        setTarget("/api/types/");
    }
}
