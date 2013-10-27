package dao.rest.requests.types;

import dao.rest.requests.Request;

public class GetAllTypesRequest extends Request {

    public GetAllTypesRequest() {
        setMethod("GET");
        setTarget("/api/types/");
    }
}
