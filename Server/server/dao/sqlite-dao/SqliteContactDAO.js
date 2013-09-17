var Interface = require('./../../interfaces/Interface'),
    SqliteDAO = require('./../../interfaces/SqliteDAO');

function SqliteContactDAO(database) { //implements ContactDAO
    Interface.ensureImplements(database, SqliteDAO);
    var _db = database;

    function isInteger(n) {
        n = parseInt(n);
        return typeof n === 'number' && parseFloat(n) == parseInt(n, 10) && !isNaN(n);
    }

    this.lastInsertedId = function (callback) {
        var sql = 'SELECT id FROM contact ORDER BY id DESC LIMIT 1;';
        _db.get(sql, callback);
    };

    this.lastInsertedContact = function (callback) {
        var sql = 'SELECT * FROM contact ORDER BY id DESC LIMIT 1;';
        _db.get(sql, callback);
    }

    this.create = function (contact, callback) {
        var sql = 'INSERT INTO contact (forename, surname, company, email, phone, street, suburb, city, state, zip, isActive) ' +
            'VALUES ($forename, $surname, $company, $email, $phone, $street, $suburb, $city, $state, $zip, 1);';
        var prepared = _db.prepare(sql);

        var values = {
            $forename: contact.getForename(),
            $surname: contact.getSurname(),
            $company: contact.getCompany(),
            $email: contact.getEmail(),
            $phone: contact.getPhone(),
            $street: contact.getStreet(),
            $suburb: contact.getSuburb(),
            $city: contact.getCity(),
            $state: contact.getState(),
            $zip: contact.getZip()
        };
        prepared.run(values, callback);
    };

    this.remove = function (id, callback) {
        if (isInteger(id) && id > 0) {
            var sql = 'UPDATE contact SET isActive = 0 WHERE id = $id;';
            var prepared = _db.prepare(sql);
            prepared.run({$id: id}, callback);
        } else {
            if (callback) {
                callback(null);
            }
        }
    };

    this.retrieveAll = function (callback) {
        var sql = 'SELECT * FROM contact WHERE isActive = 1 ORDER BY forename, surname ASC;';
        _db.all(sql, callback);
    };

    this.retrieveById = function (id, callback) {
        var sql = 'SELECT * FROM contact WHERE id = $id AND isActive = 1 LIMIT 1;';
        var prepared = _db.prepare(sql);
        prepared.get({$id: id}, callback);
    };

    this.retrieveByName = function (forename, surname, callback) {
        var sql = 'SELECT * FROM contact WHERE forename = $name AND surname = $surname LIMIT 1;';
        var prepared = _db.prepare(sql);
        prepared.get({$name: forename, $surname: surname}, callback);
    };
    this.retrieveRange = function (offset, max, callback) {
        var sql = 'SELECT * FROM contact ORDER BY forename, surname ASC WHERE isActive = 1 LIMIT $limit OFFSET $offset;';
        var prepared = _db.prepare(sql);
        prepared.all({$offset: offset, $limit: max}, callback);
    };

    this.update = function (contact, callback) {
        if (isInteger(contact.getId()) && contact.getId() > 0) {
            var sql = 'UPDATE contact SET forename = $forename, surname = $surname, ' +
                'company = $company, email = $email, phone = $phone, ' +
                'street = $street, suburb = $suburb, city = $city, state = $state, zip = $zip ' +
                'WHERE id = $id';

            var prepared = _db.prepare(sql);
            prepared.run(
                {
                    $forename: contact.getForename(),
                    $surname: contact.getSurname(),
                    $company: contact.getCompany(),
                    $email: contact.getEmail(),
                    $phone: contact.getPhone(),
                    $street: contact.getStreet(),
                    $suburb: contact.getSuburb(),
                    $city: contact.getCity(),
                    $state: contact.getState(),
                    $zip: contact.getZip()
                },
                callback
            );
        } else {
            if (callback) {
                callback(true, null);
            }
        }
    };
}

module.exports = SqliteContactDAO;

