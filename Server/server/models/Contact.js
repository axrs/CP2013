var Contact = function () {
    var id = 0,
        _forename = '',
        _surname = '',
        _company = '',
        _phone = '',
        _email = '',
        _street = '',
        _suburb = '',
        _city = '',
        _zip = '',
        _state = '';

    function isStringAndNotEmpty(value) {
        return (typeof value == 'string' && value != '');
    }

    function isInteger(n) {
        return typeof n === 'number' && parseFloat(n) == parseInt(n, 10) && !isNaN(n);
    }

    this.setId = function (value) {
        if (isInteger(value) && value > 0) {
            id = value;
        }
    };

    this.getId = function () {
        return id;
    };

    this.setForename = function (name) {
        if (isStringAndNotEmpty(name)) {
            _forename = name;
        }
    };

    this.getForename = function () {
        return _forename;
    };

    this.setSurname = function (name) {
        if (isStringAndNotEmpty(name)) {
            _surname = name;
        }
    };

    this.getSurname = function () {
        return _surname;
    };

    this.setCompany = function (value) {
        if (isStringAndNotEmpty(value)) {
            _company = value;
        }
    };

    this.getCompany = function () {
        return _company;
    };

    this.setPhone = function (value) {
        if (isStringAndNotEmpty(value)) {
            _phone = value;
        }
    }

    this.getPhone = function () {
        return _phone;
    };

    this.setStreet = function (value) {
        if (isStringAndNotEmpty(value)) {
            _street = value;
        }
    };

    this.getStreet = function () {
        return _street;
    };

    this.setEmail = function (value) {
        if (isStringAndNotEmpty(value)) {
            _email = value;
        }
    };

    this.getEmail = function () {
        return _email;
    };

    this.setSuburb = function (value) {
        if (isStringAndNotEmpty(value)) {
            _suburb = value;
        }
    };

    this.getSuburb = function () {
        return _suburb;
    };

    this.setCity = function (value) {
        if (isStringAndNotEmpty(value)) {
            _city = value;
        }
    }

    this.getCity = function () {
        return _city;
    }

    this.setZip = function (value) {
        if (isStringAndNotEmpty(value)) {
            _zip = value;
        }
    }

    this.getZip = function () {
        return _zip;
    }

    this.setState = function (value) {
        if (isStringAndNotEmpty(value)) {
            _state = value;
        }
    }

    this.getState = function () {
        return  _state;
    }

    this.setAddress = function (street, suburb, city, post, state) {
        this.setStreet(street);
        this.setSuburb(suburb);
        this.setCity(city);
        this.setZip(post);
        this.setState(state);
    }

    this.toJSON = function () {
        return {
            "id": id,
            "forename": _forename,
            "surname": _surname,
            "company": _company,
            "phone": _phone,
            "email": _email,
            "street": _street,
            "contAddrSuburb": _suburb,
            "contAddrZip": _zip,
            "contAddrState": _state
        }
    };

    this.fromJson = function (data) {
        this.setId(data.id);
        this.setForename(data.forename);
        this.setSurname(data.surname);
        this.setCompany(data.company);
        this.setPhone(data.phone);
        this.setEmail(data.email);
        this.setStreet(data.street);
        this.setSuburb(data.suburb);
        this.setCity(data.city);
        this.setZip(data.post);
        this.setState(data.state);
    }

    this.isValid = function () {
        return (isStringAndNotEmpty(_forename) && isStringAndNotEmpty(_surname));
    }
};

module.exports = Contact;