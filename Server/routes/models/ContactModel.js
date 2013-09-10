var Interface = require('../interfaces/Interface').Interface
    , DAO = require('../interfaces/DAO').DAO;

var ContactModel = function () { // implements DatabaseItem, CRUD
    var _contactId = '',
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

    this.update = function () {
        if (ContactModel._DAO && isStringAndNotEmpty(_contactId)) {
            ContactModel._DAO.update(this, function (err, result) {
                return result;
            });
        }
    }

    function isStringAndNotEmpty(value) {
        return (typeof value == 'string' && value != '');
    }

    this.setId = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contactId = value;
        }
    };

    this.setEntityIndex = function (value) {
        this.setId(value);
    };

    this.getId = function () {
        return _contactId;
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

    this.setStreetAddress = function (value) {
        if (isStringAndNotEmpty(value)) {
            _street = value;
        }
    };

    this.getStreetAddress = function () {
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
        this.setStreetAddress(street);
        this.setSuburb(suburb);
        this.setCity(city);
        this.setZip(post);
        this.setState(state);
    }

    this.toJSON = function () {
        return {
            "contId": _contactId,
            "contForename": _forename,
            "contSurname": _surname,
            "contCompany": _company,
            "contPhone": _phone,
            "contEmail": _email,
            "contAddrStreet": _street,
            "contAddrSuburb": _suburb,
            "contAddrZip": _zip,
            "contAddrState": _state
        }
    };

    this.getPropertyValues = function () {
        return [
            _contactId,
            _forename,
            _surname,
            _phone,
            _company,
            _email,
            _street,
            _suburb,
            _city,
            _zip,
            _state
        ]
    };
};


/* STATIC METHODS */

ContactModel._DAO = null;

ContactModel.prototype.getEntityName = function () {
    return 'contact';
};

ContactModel.prototype.getEntityIndex = function () {
    return 'contactId';
};

ContactModel.prototype.getEntitySchema = function () {
    return {
        contactId: String,
        forename: String,
        surname: String,
        phone: String,
        company: String,
        email: String,
        street: String,
        suburb: String,
        city: String,
        zip: String,
        state: String
    };
};

ContactModel.prototype.getPropertyNames = function () {
    return [
        'contactId',
        'forename',
        'surname',
        'phone',
        'company',
        'email',
        'street',
        'suburb',
        'city',
        'zip',
        'state'
    ];
};

ContactModel.prototype.setDAO = function (manager) {
    Interface.ensureImplements(manager, DAO);
    ContactModel._DAO = manager;
};

ContactModel.prototype.retrieve = function (id) {
    if (id && ContactModel._DAO) {
        ContactModel._DAO.retrieve(this, id, function (err, result) {
            if (result.length == 1) {
                console.log('ContactModel: retrieve(): ' + result);
                return ContactModel._createFromArray(result);
            } else {
                return null;
            }
        });
    } else {
        return null;
    }
};

ContactModel.prototype.retrieveAll = function (callback) {
    if (ContactModel._DAO) {
        ContactModel._DAO.retrieveAll(this, function (err, result) {
            var results = [];

            for (var i = 0; i < result.length; i++) {
                console.log(result[i]);
                results.push(new ContactModel().createFromArray(result[i]));
            }
            if (callback) {
                callback(err, results);
            }
            return results;
        });
    } else {
        return [];
    }
};

ContactModel.prototype.createFromArray = function (values) {
    var contact = new ContactModel();

    contact.setId(values['_id']);
    contact.setForename(values['forename']);
    contact.setSurname(values['surname']);
    contact.setPhone(values['phone']);
    contact.setCompany(values['company']);
    contact.setEmail(values['email']);
    contact.setStreetAddress(values['street']);
    contact.setSuburb(values['suburb']);
    contact.setCity(values['city']);
    contact.setZip(values['zip']);
    contact.setState(values['state']);

    return contact;
};


module.exports.ContactModel = ContactModel;