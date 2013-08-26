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
    protected String contForename;
    protected String contSurname;
    protected String contCompany;
    protected int contPhone;
    protected String contEmail;
    protected String contAddrStreet;
    protected String contAddrSuburb;
    protected String contAddrCity;
    protected int contAddrZip;
    protected String contAddrState;
    //protected Address contAddress;

    public Contact() {
    }

    public Contact(int contID, String contFirstName, String contLastName, String contCompany, int contPhone, String contEmail /* Address contAddress*/) {
        this.contID = contID;
        this.contForename = contFirstName;
        this.contSurname = contLastName;
        this.contCompany = contCompany;
        this.contPhone = contPhone;
        this.contEmail = contEmail;
        //this.contAddress = contAddress;
    }

    /**
     * Returns a Contact object generated from Json object, sent from server.
     *
     * @param data
     * @return
     */
    public static Contact fromJson(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, Contact.class);
    }

    public int getContID() {
        return contID;
    }

    public void setContID(int contID) {
        this.contID = contID;
    }

    public String getContForename() {
        return contForename;
    }

    public void setContForename(String contForename) {
        this.contForename = contForename;
    }

    public String getContSurname() {
        return contSurname;
    }

    public void setContSurname(String contSurname) {
        this.contSurname = contSurname;
    }

    public String getContCompany() {
        return contCompany;
    }

    public void setContCompany(String contCompany) {
        this.contCompany = contCompany;
    }

    public int getContPhone() {
        return contPhone;
    }

    public void setContPhone(int contPhone) {
        this.contPhone = contPhone;
    }

    public String getContEmail() {
        return contEmail;
    }

    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    public String getContAddrStreet() {
        return contAddrStreet;
    }

    public void setContAddrStreet(String contAddrStreet) {
        this.contAddrStreet = contAddrStreet;
    }

    public String getContAddrSuburb() {
        return contAddrSuburb;
    }

    public void setContAddrSuburb(String contAddrSuburb) {
        this.contAddrSuburb = contAddrSuburb;
    }

    public String getContAddrCity() {
        return contAddrCity;
    }

    public void setContAddrCity(String contAddrCity) {
        this.contAddrCity = contAddrCity;
    }

    public int getContAddrZip() {
        return contAddrZip;
    }

    public void setContAddrZip(int contAddrZip) {
        this.contAddrZip = contAddrZip;
    }

    public String getContAddrState() {
        return contAddrState;
    }

    public void setContAddrState(String contAddrState) {
        this.contAddrState = contAddrState;
    }

    /*
    public Address getContAddress() {
        return contAddress;
    }

    public void setContAddress(Address contAddress) {
        this.contAddress = contAddress;
    }
*/

    /**
     * Turns Contact object into Json object for use by server.
     *
     * @return a Json serialization of Contact.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
