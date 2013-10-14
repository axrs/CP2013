var Ring = require('ring');
var IContactDAO = require('../IContactDAO.js');
var Contact = require('../../models/Contact.js');
var LogDispatcher = require('../../utilities/LogEventDispatcher.js');

var SqliteContactDAO = Ring.create([IContactDAO], {
    _db: null,
    init: function (databaseConnection) {
        this._db = databaseConnection;
    },
    query: function (sql, values, callback) {

        if (values != null) {
            var prepared = this._db.prepare(sql);

            prepared.run(values, function (err, results) {
                if (err) {
                    LogDispatcher.log('Error running query.');
                    LogDispatcher.log(err);
                    LogDispatcher.log(sql + JSON.stringify(values));
                }
                callback(err, results);
            });
        }
        else {
            this._db.run(sql, function (err, results) {
                if (err) {
                    LogDispatcher.log('Error running query.');
                    LogDispatcher.log(err);
                    LogDispatcher.log(sql + JSON.stringify(values));
                }
                callback(err, results);
            });
        }
    },
    all: function (sql, values, callback) {
        if (values != null) {
            var prepared = this._db.prepare(sql);
            prepared.all(values, function (err, results) {
                if (err) {
                    LogDispatcher.log('Error running query.');
                    LogDispatcher.log(err);
                    LogDispatcher.log(sql + JSON.stringify(values));
                }
                callback(err, results);
            });
        } else {
            this._db.all(sql, function (err, results) {
                if (err) {
                    LogDispatcher.log('Error running query.');
                    LogDispatcher.log(err);
                    LogDispatcher.log(sql + JSON.stringify(values));
                }
                callback(err, results);
            });
        }
    },
    create: function (contact, callback) {
        var sql = '' +
            'INSERT INTO Contact ' +
            '(Name, MiddleName, Surname, Company, Phone, Email, Address, Suburb, City, Country, Post, State) ' +
            'VALUES ' +
            '($name, $middleName, $surname, $company, $phone, $email, $address, $suburb, $city, $country, $post, $state);';

        var values = {
            $name: contact.getName(),
            $middleName: contact.getMiddleName(),
            $surname: contact.getSurname(),
            $company: contact.getCompany(),
            $phone: contact.getPhone(),
            $email: contact.getEmail(),
            $address: contact.getAddress(),
            $suburb: contact.getSuburb(),
            $city: contact.getCity(),
            $country: contact.getCountry(),
            $state: contact.getState(),
            $post: contact.getPost()
        };

        this.query(sql, values, function (err, res) {
            callback(err, null);
        });

    },
    retrieveAll: function (callback) {
        var sql = 'SELECT * FROM Contact ORDER BY Name, Surname ASC;';
        this.all(sql, null, function (err, result) {
            if (result.length) {
                var contacts = [];
                for (var i = 0; i < result.length; i++) {
                    contacts.push(SqliteContactDAO.ContactFromDatabase(result[i]));
                }
                callback(err, contacts);
            } else {
                callback(err, null);
            }
        });
    },
    retrieveById: function (id, callback) {
        var sql = 'SELECT * FROM Contact WHERE ContactId = $id LIMIT 1;';

        this.all(sql, {$id: id}, function (err, result) {
            if (result.length) {
                callback(err, SqliteContactDAO.ContactFromDatabase(result[0]));
            } else {
                callback(err, null);
            }
        });
    },
    retrieveByName: function (name, surname, callback) {
        var sql = 'SELECT * FROM Contact WHERE Name = $name AND Surname = $surname LIMIT 1;';

        this.all(sql, {$name: name, $surname: surname}, function (err, result) {
            if (result.length) {
                callback(err, SqliteContactDAO.ContactFromDatabase(result[0]));
            } else {
                callback(err, null);
            }
        });
    },
    retrieveRange: function (offset, limit, callback) {
        var sql = 'SELECT * FROM Contact ORDER BY Name, Surname ASC LIMIT $limit OFFSET $offset;';

        this.all(sql, {$limit: limit, $offset: offset}, function (err, result) {
            if (result.length) {
                var contacts = [];
                for (var i = 0; i < result.length; i++) {
                    contacts.push(SqliteContactDAO.ContactFromDatabase(result[i]));
                }
                callback(err, contacts);
            } else {
                callback(err, null);
            }
        });
    },
    update: function (contact, callback) {
        var sql = '' +
            'UPDATE Contact ' +
            'SET Name=$name, MiddleName=$middleName, Surname=$surname, ' +
            '    Company=$company, Phone=$phone, Email=$email, ' +
            '    Address=$address, Suburb=$suburb, City=$city, Country=$country, State=$state, Post=$post ' +
            'WHERE ContactId=$id;';

        var values = {
            $id: contact.getId(),
            $name: contact.getName(),
            $middleName: contact.getMiddleName(),
            $surname: contact.getSurname(),

            $company: contact.getCompany(),
            $phone: contact.getPhone(),
            $email: contact.getEmail(),

            $address: contact.getAddress(),
            $suburb: contact.getSuburb(),
            $city: contact.getCity(),
            $country: contact.getCountry(),
            $state: contact.getState(),
            $post: contact.getPost()
        };

        this.query(sql, values, function (err, result) {
            callback(err, null);
        });
    },
    remove: function (contact, callback) {
        var sql = 'UPDATE Contact SET isActive=0 WHERE ContactId=$id;';
        this.query(sql, null, function (err, result) {
            callback(err, null);
        });
    },
    lastInsertedId: function (callback) {
        var sql = 'SELECT ContactId FROM Contact ORDER BY ContactId DESC LIMIT 1;';

        this.all(sql, null, function (err, result) {
            if (result.length) {
                callback(err, result[0].ContactId);
            } else {
                callback(err, null);
            }
        });
    },
    lastInsertedContact: function (callback) {
        var sql = 'SELECT * FROM Contact ORDER BY ContactId DESC LIMIT 1;';

        this.all(sql, null, function (err, result) {
            if (result.length) {
                callback(err, SqliteContactDAO.ContactFromDatabase(result[0]));
            } else {
                callback(err, null);
            }
        });
    }
});


SqliteContactDAO.ContactFromDatabase = function (row) {
    var c = new Contact();
    c.setId(row.ContactId);
    c.setName(row.Name);
    c.setMiddleName(row.MiddleName);
    c.setSurname(row.Surname);

    c.setCompany(row.company);
    c.setEmail(row.email);
    c.setPhone(row.phone);

    c.setAddress(row.address, row.suburb, row.city, row.country, row.state, row.post);

    return c;
};

module.exports = SqliteContactDAO;