var ContactModel = function () { // implements DatabaseItem
    var _contId = 0,
        _contForename = '',
        _contSurname = '',
        _contCompany = '',
        _contPhone = '',
        _contEmail = '',
        _contAddrStreet = '',
        _contAddrSuburb = '',
        _contAddrCity = '',
        _contAddrZip = '',
        _contAddrState = '';

    function isStringAndNotEmpty(value) {
        return (typeof value == 'string' && value != '');
    }

    function isInteger(value) {
        return typeof value === 'number' && value % 1 == 0;
    }


    this.setId = function (value) {
        if (isInteger(value) && value > 0) {
            _contId = value;
        }
    };

    this.getId = function () {
        return _contId;
    };

    this.getEntityIndex = function () {
        return this.getId();
    };

    this.setForename = function (name) {
        if (isStringAndNotEmpty(name)) {
            _contForename = name;
        }
    };

    this.getForename = function () {
        return _contForename;
    };

    this.setSurname = function (name) {
        if (isStringAndNotEmpty(name)) {
            _contSurname = name;
        }
    };

    this.getSurname = function () {
        return _contSurname;
    };

    this.setCompany = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contCompany = value;
        }
    };

    this.getCompany = function () {
        return _contCompany;
    };

    this.setPhone = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contPhone = value;
        }
    }

    this.getPhone = function () {
        return _contPhone;
    };

    this.setStreetAddress = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contAddrStreet = value;
        }
    };

    this.getStreetAddress = function () {
        return _contAddrStreet;
    };

    this.setEmail = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contEmail = value;
        }
    };

    this.getEmail = function () {
        return _contEmail;
    };

    this.setSuburb = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contAddrSuburb = value;
        }
    };

    this.getSuburb = function () {
        return _contAddrSuburb;
    };

    this.setCity = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contAddrCity = value;
        }
    }

    this.getCity = function () {
        return _contAddrCity;
    }

    this.setZip = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contAddrZip = value;
        }
    }

    this.getZip = function () {
        return _contAddrZip;
    }

    this.setState = function (value) {
        if (isStringAndNotEmpty(value)) {
            _contAddrState = value;
        }
    }

    this.getState = function () {
        return  _contAddrState;
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
            "contId": _contId,
            "contForename": _contForename,
            "contSurname": _contSurname,
            "contCompany": _contCompany,
            "contPhone": _contPhone,
            "contEmail": _contEmail,
            "contAddrStreet": _contAddrStreet,
            "contAddrSuburb": _contAddrSuburb,
            "contAddrZip": _contAddrZip,
            "contAddrState": _contAddrState
        }
    };

    this.getPropertyArray = function () {
        return [
            _contId,
            _contForename,
            _contSurname,
            _contPhone,
            _contCompany,
            _contEmail,
            _contAddrStreet,
            _contAddrSuburb,
            _contAddrCity,
            _contAddrZip,
            _contAddrState
        ]
    };
};


/* STATIC METHODS */

ContactModel.prototype.getEntityName = function () {
    return 'contact';
};

ContactModel.prototype.getEntitySchema = function () {
    return [
        'contId',
        'contForename',
        'contSurname',
        'contPhone',
        'contCompany',
        'contEmail',
        'contAddrStreet',
        'contAddrSuburb',
        'contAddrCity',
        'contAddrZip',
        'contAddrState'
    ];
};

module.exports.ContactModel = ContactModel;

/*
 module.exports = function (database) {
 return{
 all: function (callback) {
 database.all('SELECT * FROM contact ORDER BY contForename ASC, contSurname ASC;', callback);
 },
 findById: function (id, callback) {
 database.get('SELECT * FROM contact WHERE contId = ?;', id, callback);
 },
 matchName: function (name, surname, callback) {
 database.get('SELECT * FROM contact WHERE contForename = ? AND contSurname = ?;', [name, surname], callback);
 },
 count: function (callback) {
 database.get('SELECT count(*) FROM contact', null, callback);
 },
 insert: function (data, callback) {
 var statement = database.prepare(
 'INSERT INTO contact ' +
 '(contForename, contSurname, contCompany, contPhone, contEmail, ' +
 'contAddrStreet, contAddrSuburb, contAddrCity, contAddrZip, contAddrState)' +
 ' VALUES (?,?,?,?,?,' +
 '?,?,?,?,?);'
 );
 statement.run(
 [
 data.contForename,
 data.contSurname,
 (typeof data.contCompany === 'undefined') ? '' : data.contCompany,
 (typeof data.contPhone === 'undefined') ? '' : data.contPhone,
 (typeof data.contEmail === 'undefined') ? '' : data.contEmail,
 (typeof data.contAddrStreet === 'undefined') ? '' : data.contAddrStreet,
 (typeof data.contAddrSuburb === 'undefined') ? '' : data.contAddrSuburb,
 (typeof data.contAddrCity === 'undefined') ? '' : data.contAddrCity,
 (typeof data.contAddrZip === 'undefined') ? '' : data.contAddrZip,
 (typeof data.contAddrState === 'undefined') ? '' : data.contAddrState
 ],
 callback
 );
 },
 update: function (id, data, callback) {
 var statement = database.prepare(
 'UPDATE contact SET contForename = ?, contSurname = ?, contCompany = ?, contPhone = ?, contEmail = ?, ' +
 'contAddrStreet = ?, contAddrSuburb = ?, contAddrCity = ?, contAddrZip = ?, contAddrState = ? ' +
 'WHERE contId = ?;'
 );
 statement.run(
 [
 data.contForename,
 data.contSurname,
 (typeof data.contCompany === 'undefined') ? '' : data.contCompany,
 (typeof data.contPhone === 'undefined') ? '' : data.contPhone,
 (typeof data.contEmail === 'undefined') ? '' : data.contEmail,
 (typeof data.contAddrStreet === 'undefined') ? '' : data.contAddrStreet,
 (typeof data.contAddrSuburb === 'undefined') ? '' : data.contAddrSuburb,
 (typeof data.contAddrCity === 'undefined') ? '' : data.contAddrCity,
 (typeof data.contAddrZip === 'undefined') ? '' : data.contAddrZip,
 (typeof data.contAddrState === 'undefined') ? '' : data.contAddrState,
 id
 ],
 callback
 );
 }
 }
 }
 */