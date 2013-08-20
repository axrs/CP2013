import com.google.gson.Gson;

import java.awt.image.BufferedImage;
import java.sql.Date;
import javax.json.*;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 12/08/13
 * Time: 9:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceProvider extends Contact {

    protected String servBio;
    protected Date servEmployed;
    protected BufferedImage servPortrait;
    protected Date servTerminated;
    protected boolean servIsActive;

    public ServiceProvider(int contID, String contFirstName, String contLastName, String contCompany, int contPhone, String contEmail, Address contAddress, String servBio, Date servEmployed, BufferedImage servPortrait, Date servTerminated, boolean servIsActive) {
        super(contID, contFirstName, contLastName, contCompany, contPhone, contEmail, contAddress);
        this.servBio = servBio;
        this.servEmployed = servEmployed;
        this.servPortrait = servPortrait;
        this.servTerminated = servTerminated;
        this.servIsActive = servIsActive;
    }

    /**
     * Turns ServiceProvider object into Json object for use by server.
     * @return a Json serialization of ServiceProvider.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Returns a ServiceProvider object generated from Json object, sent from server.
     * @param data
     * @return
     */
    public static ServiceProvider fromJson(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, ServiceProvider.class);
    }
}
