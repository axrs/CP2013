package Models;

import Controllers.ContactsController;

public class Contact implements CRUDModel {
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
    private String contPhone;
    /**
     * Contact Street Address
     */
    private String contAddrStreet;
    /**
     * Address Suburb
     */
    private String contAddrSuburb;
    /**
     * Address City
     */
    private String contAddrCity;
    /**
     * Address ZIP/Post Code
     */
    private String contAddrZip;
    /**
     * Address State
     */
    private String contAddrState;
    /**
     * Contact Id
     */
    private int contId;

    /**
     * Contact Default Constructor
     */
    public Contact(String contForename, String contSurname) {
        this.contForename = contForename;
        this.contSurname = contSurname;
    }

    /**
     * Constructor
     */
    public Contact() {
    }

    /**
     * Gets the contact id
     *
     * @return contact id
     */
    public int getContId() {
        return contId;
    }

    /**
     * Sets the contact id
     *
     * @param contId contact id
     */
    public void setContId(int contId) {
        this.contId = contId;
    }

    /**
     * Gets the contacts first name
     *
     * @return contacts first name
     */
    public String getContFirstName() {
        return contForename;
    }

    /**
     * Sets the contacts first name
     *
     * @param contForename first name
     */
    public void setContForename(String contForename) {
        this.contForename = contForename;
    }

    /**
     * Gets the contacts surname
     *
     * @return Surname
     */
    public String getContSurname() {
        return contSurname;
    }

    /**
     * Sets the contacts surname
     *
     * @param contSurname surname
     */
    public void setContSurname(String contSurname) {
        this.contSurname = contSurname;
    }

    /**
     * Gets the contacts company
     *
     * @return company
     */
    public String getContCompany() {
        return contCompany;
    }

    /**
     * Sets the contacts company
     *
     * @param contCompany company
     */
    public void setContCompany(String contCompany) {
        this.contCompany = contCompany;
    }

    /**
     * Gets the contacts email address
     *
     * @return contact email
     */
    public String getContEmail() {
        return contEmail;
    }

    /**
     * Sets the contacts email address
     *
     * @param contEmail email address
     */
    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    /**
     * Gets the contacts phone number
     *
     * @return phone number
     */
    public String getContPhone() {
        return contPhone;
    }

    /**
     * Sets the contacts phone number
     *
     * @param contPhone phone number
     */
    public void setContPhone(String contPhone) {
        this.contPhone = contPhone;
    }

    /**
     * Gets the contacts street address
     *
     * @return street address
     */
    public String getContAddrStreet() {
        return contAddrStreet;
    }

    /**
     * Sets the contacts street address
     *
     * @param contAddrStreet street address
     */
    public void setContAddrStreet(String contAddrStreet) {
        this.contAddrStreet = contAddrStreet;
    }

    /**
     * Gets the contacts address suburb
     *
     * @return contacts address suburb
     */
    public String getContAddrSuburb() {
        return contAddrSuburb;
    }

    /**
     * Sets the contacts address suburb
     *
     * @param contAddrSuburb address suburb
     */
    public void setContAddrSuburb(String contAddrSuburb) {
        this.contAddrSuburb = contAddrSuburb;
    }

    /**
     * Gets the contacts address city
     *
     * @return address city
     */
    public String getContAddrCity() {
        return contAddrCity;
    }

    /**
     * Sets the contacts address city
     *
     * @param contAddrCity city
     */
    public void setContAddrCity(String contAddrCity) {
        this.contAddrCity = contAddrCity;
    }

    /**
     * Gets the contacts post code
     *
     * @return post code
     */
    public String getContAddrZip() {
        return contAddrZip;
    }

    /**
     * Sets the contacts post code
     *
     * @param contAddrZip post code
     */
    public void setContAddrZip(String contAddrZip) {
        this.contAddrZip = contAddrZip;
    }

    /**
     * Gets the contacts address state
     *
     * @return state
     */
    public String getContAddrState() {
        return contAddrState;
    }

    /**
     * Sets the contacts address state
     *
     * @param contAddrState state
     */
    public void setContAddrState(String contAddrState) {
        this.contAddrState = contAddrState;
    }

    private void replaceInstance(Contact c) {
        contId = c.contId;
        contForename = c.contForename;
        contSurname = c.contSurname;
        contEmail = c.contEmail;
        contCompany = c.contCompany;
        contPhone = c.contPhone;

        contAddrStreet = c.contAddrStreet;
        contAddrSuburb = c.contAddrSuburb;
        contAddrCity = c.contAddrCity;
        contAddrState = c.contAddrState;
        contAddrZip = c.contAddrZip;

    }

    @Override
    public boolean create() {
        if (this.getContId() == 0) {
            ContactsController.getInstance().createContact(this);
        }
        return true;
    }

    @Override
    public boolean retrieve(int id) {
        Contact c = ContactsController.getInstance().getContact(id);

        boolean result = (c != null);
        if (result) {
            replaceInstance(c);
        }
        return result;
    }

    @Override
    public boolean retrieve() {
        boolean result = false;
        Contact c = null;

        if (this.getContId() != 0) {
            c = ContactsController.getInstance().getContact(getContId());
            result = (c != null);
        }

        if (result)
            replaceInstance(c);
        return result;
    }

    @Override
    public boolean update() {
        boolean result = true;
        if (getContId() != 0) {
            ContactsController.getInstance().updateContact(this);
        } else {
            result = create();
        }
        return result;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
