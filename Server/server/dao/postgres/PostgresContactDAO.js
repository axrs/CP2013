var Ring = require('ring');
var IContactDAO = require('../IContactDAO.js');
var Contact = require('../../models/Contact.js');
var LogDispatcher = require('../../utilities/LogEventDispatcher.js');

var PostgresContactDAO = Ring.create([IContactDAO], {
    _db: null,
    init: function (databaseConnection) {
        this._db = databaseConnection;
    },
    ContactFromDatabase: function (row) {
        var c = new Contact();
        c.setId(row.ContactId);
        c.setName(row.Name);
        c.setMiddleName(row.MiddleName);
        c.setSurname(row.Surname);
        c.setSalutation(row.Salutation);
        return c;
    },
    query: function (sql, values, callback) {
        this._db.connect(function (err, client, done) {
            var sql = 'INSERT INTO contact (Salutation, Name, MiddleName, Surname) VALUES ($1, $2, $3, $4);';
            client.query(sql, values, function (err, result) {
                done();
                if (err) {
                    LogDispatcher.log('Error running query.');
                    LogDispatcher.log(err);
                }
                callback(err, result);
            });
        });
    },
    create: function (contact, callback) {

        var sql = 'INSERT INTO contact (Salutation, Name, MiddleName, Surname) VALUES ($1, $2, $3, $4);';

        this.query(sql, [contact.getSalutation(), contact.getName(), contact.getMiddleName(), contact.getSurname()], function (err, result) {
            callback(err, null);
        });
    },
    retrieveAll: function (callback) {
        var sql = 'SELECT * FROM contact ORDER BY Name, Surname ASC;';
        this.query(sql, [], function (err, result) {
            if (result.rowCount) {
                var contacts = [];
                for (var i = 0; i < result.rowCount; i++) {
                    contacts.push(this.ContactFromDatabase(result.rows[i]));
                }
                callback(err, contacts);
            } else {
                callback(err, null);
            }
        });
    },
    retrieveById: function (id, callback) {
        var sql = 'SELECT * FROM contact WHERE ContactId = $1 LIMIT 1;';

        this.query(sql, [id], function (err, result) {
            if (result.rowCount) {
                callback(err, this.ContactFromDatabase(result.row[0]));
            } else {
                callback(err, null);
            }
        });
    },
    retrieveByName: function (name, surname, callback) {
        var sql = 'SELECT * FROM contact WHERE Name = $1 AND Surname = $2 LIMIT 1;';

        this.query(sql, [name, surname], function (err, result) {
            if (result.rowCount) {
                callback(err, this.ContactFromDatabase(result.row[0]));
            } else {
                callback(err, null);
            }
        });
    },
    retrieveRange: function (offset, max, callback) {

        var sql = 'SELECT * FROM contact ORDER BY Name, Surname ASC LIMIT $1 OFFSET $2;';

        this.query(sql, [max, offset], function (err, result) {
            if (result.rowCount) {
                var contacts = [];
                for (var i = 0; i < result.rowCount; i++) {
                    contacts.push(this.ContactFromDatabase(result.rows[i]));
                }
                callback(err, contacts);
            } else {
                callback(err, null);
            }
        });
    },
    update: function (contact, callback) {
        var sql = 'UPDATE Contact SET Name = $2, MiddleName=$3, Surname=$4, Salutation=$5 WHERE ContactId = $1;';
        this.query(
            sql,
            [
                contact.getId(),
                contact.getName(),
                contact.getMiddleName(),
                contact.getSurname(),
                contact.getSalutation()
            ],
            function (err, result) {
                callback(err, null);
            });
    },
    remove: function (contact, callback) {
        throw new Error('Method Not Implemented.');
    },
    lastInsertedId: function (callback) {
        var sql = 'SELECT ContactId FROM Contact ORDER BY ContactId DESC LIMIT 1;';

        this.query(sql, [], function (err, result) {
            if (result.rowCount) {
                callback(err, this.ContactFromDatabase(result.row[0]));
            } else {
                callback(err, null);
            }
        });
    },
    lastInsertedContact: function (callback) {
        var sql = 'SELECT * FROM Contact ORDER BY ContactId DESC LIMIT 1;';

        this.query(sql, [], function (err, result) {
            if (result.rowCount) {
                callback(err, this.ContactFromDatabase(result.row[0]));
            } else {
                callback(err, null);
            }
        });
    }
});
module.exports = PostgresContactDAO;