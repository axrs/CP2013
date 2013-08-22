import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;


/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 15/08/13
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class RestAPI {


    private static HttpURLConnection _CONNECTION = null;


    public RestAPI() {

    }

    /**
     * Reads Input Stream to String
     *
     * @param stream
     * @return
     * @throws IOException
     */
    private static String ReadStream(InputStream stream) throws IOException {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[4096];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            result = buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
        return result;
    }


    /**
     * Method to retrieve data from REST API server database.
     */
    public static RESTResult get(String endPoint) {

        RESTResult result = new RESTResult();

        try {

            //  making sure that the string is right, has http (fully lower case)
            if (!endPoint.toLowerCase().substring(0, 7).equals("http://")) {
                endPoint = "http://" + endPoint;
            }

            URL url = new URL(endPoint);
            _CONNECTION = (HttpURLConnection) url.openConnection();
            _CONNECTION.setDoOutput(true);
            _CONNECTION.setInstanceFollowRedirects(true);
            _CONNECTION.setRequestMethod("GET");
            result = new RESTResult(_CONNECTION.getResponseCode(), ReadStream(_CONNECTION.getInputStream()));

            _CONNECTION.disconnect();

        } catch (Exception ex) {
            //throw new RuntimeException(ex);
        }

        return result;
    }

    /**
     * Method to create data for REST API server database.
     */
    public static void post() {
        throw new NotImplementedException();
    }

    ;

    /**
     * Method to remove data from REST API server database.
     */
    public static void delete() {
        throw new NotImplementedException();
    }

    ;

    /**
     * Method to update data from REST API server database.
     */
    public void put() {
        throw new NotImplementedException();
    }

    ;
}
