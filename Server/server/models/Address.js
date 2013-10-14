var Ring = require('ring');

var Address = Ring.create({
    _address: '',
    _suburb: '',
    _city: '',
    _country: '',
    _state: '',
    _post: '',

    isStringAndNotEmpty: function (value) {
        return (typeof value == 'string' && value != '');
    },

    isInteger: function (n) {
        return typeof n === 'number' && parseFloat(n) == parseInt(n, 10) && !isNaN(n);
    },
    setAddress: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._address = value;
        }
    },
    getAddress: function () {
        return this._address;
    },
    getCity: function () {
        return this._city;
    },
    setCity: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._city = value;
        }
    },
    getSuburb: function () {
        return this._suburb;
    },
    setSuburb: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._suburb = value;
        }
    },
    getCountry: function () {
        return this._country;
    },
    setCountry: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._country = value;
        }
    },
    getState: function () {
        return this._state;
    },
    setState: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._state = value;
        }
    },
    getPost: function () {
        return this._post;
    },
    setPost: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._post = value;
        }
    },

    toJSON: function () {
        return {
            "address": this._address,
            "suburb": this._suburb,
            "city": this._city,
            "country": this._country,
            "state": this._state,
            "post": this._post
        }
    }
});

/**
 *
 * @param data
 * @returns {Address}
 */
Address.fromJSON = function (data) {
    var address = new Address();
    address.setAddress(data.address);
    address.setSuburb(data.suburb);
    address.setCity(data.city);
    address.setCountry(data.country);
    address.setState(data.state);
    address.setPost(data.post);
    return address;
};

module.exports = Address;