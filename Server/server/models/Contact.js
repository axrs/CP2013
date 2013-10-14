var Ring = require('ring');
var Address = require('./Address.js');
var Utilities = require('../utilities/Utilities.js');

var Contact = Ring.create({
    _contactId: 0,
    _salutation: '',
    _name: '',
    _middleName: '',
    _surname: '',
    _address: new Address(),
    _email: '',
    _phone: '',
    _company: '',

    isValid: function () {
        return (Utilities.isStringAndNotEmpty(this._name) && Utilities.isStringAndNotEmpty(this._surname));
    },

    getId: function () {
        return this._contactId;
    },

    setId: function (value) {
        if (Utilities.isInteger(value) && value > 0) {
            this._contactId = value;
        }
    },

    setSalutation: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._salutation = value;
        }
    },

    getSalutation: function () {
        return this._salutation;
    },

    getName: function () {
        return this._name;
    },

    setName: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._name = value;
        }
    },

    setMiddleName: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._middleName = value;
        }
    },

    getMiddleName: function () {
        return this._middleName;
    },

    setSurname: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._surname = value;
        }
    },

    getSurname: function () {
        return this._surname;
    },

    getAddress: function () {
        return this._address.getAddress();
    },
    getSuburb: function () {
        return this._address.getSuburb();
    },
    getCity: function () {
        return this._address.getCity();
    },
    getCountry: function () {
        return this._address.getCountry();
    },
    getState: function () {
        return this._address.getState();
    },
    getPost: function () {
        return this._address.getPost();
    },
    setAddress: function (street, suburb, city, country, state, post) {
        this._address.setAddress(street);
        this._address.setSuburb(suburb);
        this._address.setCity(city);
        this._address.setCountry(country);
        this._address.setState(state);
        this._address.setPost(post);
    },


    getEmail: function () {
        return this._email;
    },

    setEmail: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._email = value;
        }
    },

    getPhone: function () {
        return this._phone;
    },

    setPhone: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._phone = value;
        }
    },

    getCompany: function () {
        return this._company;
    },

    setCompany: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._company = value;
        }
    },

    toJSON: function () {
        return {
            "contactId": this._contactId,
            "name": this._name,
            "middleName": this._middleName,
            "surname": this._surname,

            "company": this._company,
            "phone": this._phone,
            "email": this._email,

            "address": this._address.getAddress(),
            "suburb": this._address.getSuburb(),
            "city": this._address.getCity(),
            "country": this._address.getCountry(),
            "state": this._address.getState(),
            "post": this._address.getPost()
        }
    }
});

/**
 *
 * @param data
 * @returns {Contact}
 */
Contact.fromJSON = function (data) {
    var contact = new Contact();

    contact.setId(data.contactId);
    contact.setSalutation(data.salutation);
    contact.setName(data.name);
    contact.setMiddleName(data.middleName);
    contact.setSurname(data.surname);

    contact.setCompany(data.company);
    contact.setPhone(data.phone);
    contact.setEmail(data.email);


    contact.setAddress(data.address, data.suburb, data.city, data.country, data.state, data.post);
    return contact;
};

module.exports = Contact;