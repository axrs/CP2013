import com.google.gson.Gson;

import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 12/08/13
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceHours {
    protected int contID;
    protected int servHrsDay;
    protected Time servHrsStart;
    protected Time servHrsEnd;

    public ServiceHours() {
    }

    public ServiceHours(int contID, int servHrsDay, Time servHrsStart, Time servHrsEnd) {
        this.contID = contID;
        this.servHrsDay = servHrsDay;
        this.servHrsStart = servHrsStart;
        this.servHrsEnd = servHrsEnd;
    }

    /**
     * Turns ServiceHours object into Json object for use by server.
     * @return a Json serialization of ServiceHours.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Returns a ServiceHours object generated from Json object, sent from server.
     * @param data
     * @return
     */
    public static ServiceHours fromJson(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, ServiceHours.class);
    }
}
