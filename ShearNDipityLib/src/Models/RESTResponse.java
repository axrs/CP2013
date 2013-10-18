package Models;

public class RESTResponse {
    private int status = 0;
    private String body = "";

    public RESTResponse() {

    }

    public RESTResponse(int status, String body) {

        this.status = status;
        this.body = body;
    }

    public RESTResponse(int status) {

        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}