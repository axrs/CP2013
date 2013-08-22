/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 22/08/13
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class RESTResult {

    private int statusCode = 0;
    private String serverResponse = "";

    public RESTResult(){

    }

    public RESTResult(int status, String response) {
        statusCode = status;
        serverResponse = response;
    }

    public String getServerResponse(){
        return serverResponse;
    }

    public int getStatusCode(){
        return statusCode;
    }
}
