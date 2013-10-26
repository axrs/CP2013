package Models;

import Controllers.ContactsController;

public class Contact implements CRUDModel {
    /**
     * Contact First Name
     */
    private String name;
    /**
     * Contact Surname
     */
    private String middleName;
    private String surname;
    /**
     * Contact Company Name
     */
    private String company;
    /**
     * Contact Email Address
     */
    private String email;
    /**
     * Contact Phone Number
     */
    private String phone;
    /**
     * Contact Street Address
     */
    private String street;
    /**
     * Address Suburb
     */
    private String suburb;
    /**
     * Address City
     */
    private String city;
    /**
     * Address ZIP/Post Code
     */
    private String post;
    /**
     * Address State
     */
    private String state;
    /**
     * Contact Id
     */
    private int contactId;

    /**
     * Contact Default Constructor
     */
    public Contact(String contForename, String contSurname) {
        this.name = contForename;
        this.surname = contSurname;
    }

    /**
     * Constructor
     */
    public Contact() {
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the contacts first name
     *
     * @param name first name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the contact id
     *
     * @return contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact id
     *
     * @param contactId contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets the contacts first name
     *
     * @return contacts first name
     */
    public String getContFirstName() {
        return name;
    }

    /**
     * Gets the contacts surname
     *
     * @return Surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the contacts surname
     *
     * @param surname surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the contacts company
     *
     * @return company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the contacts company
     *
     * @param company company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Gets the contacts email address
     *
     * @return contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the contacts email address
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the contacts phone number
     *
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the contacts phone number
     *
     * @param phone phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the contacts street address
     *
     * @return street address
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the contacts street address
     *
     * @param street street address
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the contacts address suburb
     *
     * @return contacts address suburb
     */
    public String getSuburb() {
        return suburb;
    }

    /**
     * Sets the contacts address suburb
     *
     * @param suburb address suburb
     */
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    /**
     * Gets the contacts address city
     *
     * @return address city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the contacts address city
     *
     * @param city city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the contacts post code
     *
     * @return post code
     */
    public String getPost() {
        return post;
    }

    /**
     * Sets the contacts post code
     *
     * @param post post code
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * Gets the contacts address state
     *
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the contacts address state
     *
     * @param state state
     */
    public void setState(String state) {
        this.state = state;
    }

    private void replaceInstance(Contact c) {
        contactId = c.contactId;
        name = c.name;
        surname = c.surname;
        email = c.email;
        company = c.company;
        phone = c.phone;

        street = c.street;
        suburb = c.suburb;
        city = c.city;
        state = c.state;
        post = c.post;

    }

    @Override
    public boolean create() {
        if (this.getContactId() == 0) {
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

        if (this.getContactId() != 0) {
            c = ContactsController.getInstance().getContact(getContactId());
            result = (c != null);
        }

        if (result)
            replaceInstance(c);
        return result;
    }

    @Override
    public boolean update() {
        boolean result = true;
        if (getContactId() != 0) {
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
