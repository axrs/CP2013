package Models;

/**
 * Contact
 *
 * Generic representation of a typical contact
 *
 * Created by xander on 8/22/13.
 */
public class Contact {

    /**
     * Contact Id
     */
    private int contId;

    /**
     * Contact First Name
     */
    private String contForename;

    /**
     * Contact Surname
     */
    private String contSurname;

    /**
     * Contact Company Name
     */
    private String contCompany;

    /**
     * Contact Email Address
     */
    private String contEmail;

    /**
     * Contact Phone Number
     */
    private int contPhone;

    /**
     * Contact Street Address
     */
    private String contAddrStreet;

    /**
     * Address Suburb
     */
    private String contAddrSubutb;


    /**
     * Address City
     */
    private String contAddrCity;

    /**
     * Address ZIP/Post Code
     */
    private int contAddrZip;

    /**
     * Address State
     */
    private String contAddrState;

    /**
     * Constructor
     */
    public Contact(){
    }

    /**
     * Gets the contact id
     * @return contact id
     */
    public int getContId() {
        return contId;
    }

    /**
     * Sets the contact id
     * @param contId contact id
     */
    public void setContId(int contId) {
        this.contId = contId;
    }

    /**
     * Gets the contacts first name
     * @return contacts first name
     */
    public String getContFirstName() {
        return contForename;
    }

    /**
     * Sets the contacts first name
     * @param contForename first name
     */
    public void setContForename(String contForename) {
        this.contForename = contForename;
    }

    /**
     * Gets the contacts surname
     * @return Surname
     */
    public String getContSurname() {
        return contSurname;
    }

    /**
     * Sets the contacts surname
     * @param contSurname surname
     */
    public void setContSurname(String contSurname) {
        this.contSurname = contSurname;
    }

    /**
     * Gets the contacts company
     * @return company
     */
    public String getContCompany() {
        return contCompany;
    }

    /**
     * Sets the contacts company
     * @param contCompany company
     */
    public void setContCompany(String contCompany) {
        this.contCompany = contCompany;
    }

    /**
     * Gets the contacts email address
     * @return contact email
     */
    public String getContEmail() {
        return contEmail;
    }

    /**
     * Sets the contacts email address
     * @param contEmail email address
     */
    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    /**
     * Gets the contacts phone number
     * @return phone number
     */
    public int getContPhone() {
        return contPhone;
    }

    /**
     * Sets the contacts phone number
     * @param contPhone phone number
     */
    public void setContPhone(int contPhone) {
        this.contPhone = contPhone;
    }

    /**
     * Gets the contacts street address
     * @return street address
     */
    public String getContAddrStreet() {
        return contAddrStreet;
    }

    /**
     * Sets the contacts street address
     * @param contAddrStreet street address
     */
    public void setContAddrStreet(String contAddrStreet) {
        this.contAddrStreet = contAddrStreet;
    }

    /**
     * Gets the contacts address suburb
     * @return contacts address suburb
     */
    public String getContAddrSubutb() {
        return contAddrSubutb;
    }

    /**
     * Sets the contacts address suburb
     * @param contAddrSubutb address suburb
     */
    public void setContAddrSubutb(String contAddrSubutb) {
        this.contAddrSubutb = contAddrSubutb;
    }

    /**
     * Gets the contacts address city
     * @return address city
     */
    public String getContAddrCity() {
        return contAddrCity;
    }

    /**
     * Sets the contacts address city
     * @param contAddrCity city
     */
    public void setContAddrCity(String contAddrCity) {
        this.contAddrCity = contAddrCity;
    }

    /**
     * Gets the contacts post code
     * @return post code
     */
    public int getContAddrZip() {
        return contAddrZip;
    }

    /**
     * Sets the contacts post code
     * @param contAddrZip post code
     */
    public void setContAddrZip(int contAddrZip) {
        this.contAddrZip = contAddrZip;
    }

    /**
     * Gets the contacts address state
     * @return state
     */
    public String getContAddrState() {
        return contAddrState;
    }

    /**
     * Sets the contacts address state
     * @param contAddrState state
     */
    public void setContAddrState(String contAddrState) {
        this.contAddrState = contAddrState;
    }
}
