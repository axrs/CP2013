import javax.json.*;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 12/08/13
 * Time: 8:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class Contact {

    protected int contID;
    protected String contFirstName;
    protected String contLastName;
    protected String contCompany;
    protected int contPhone;
    protected String contEmail;
    protected String contAddress;

    public Contact() {
    }

    public Contact(int contID, String contFirstName, String contLastName, String contCompany, int contPhone, String contEmail, String contAddress) {
        this.contID = contID;
        this.contFirstName = contFirstName;
        this.contLastName = contLastName;
        this.contCompany = contCompany;
        this.contPhone = contPhone;
        this.contEmail = contEmail;
        this.contAddress = contAddress;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Contact fromJson(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, Contact.class);
    }
}
