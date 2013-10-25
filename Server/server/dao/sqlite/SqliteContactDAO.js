var Ring = require('ring');
var IContactDAO = require('../IContactDAO.js');
var Contact = require('../../models/Contact.js');
var SqliteHelper = require('./SqliteHelper.js');

var SqliteContactDAO = Ring.create([SqliteHelper, IContactDAO], {
    init: function (databaseConnection) {
        this.$super(databaseConnection);
    },

    /**
     * Called after attempting to create a contact
     * @callback SqliteContactDAO~GeneralCallback
     * @param {Error} err - Error object containing the error message from SQLite
     */


    /**
     * Creates a contact into the Database.
     * @param {Contact} contact
     * @param {SqliteContactDAO~GeneralCallback} [callback]
     */
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
            if (callback) {
                callback(err);
            }
        });

    },

    /**
     * Called after retrieving all contacts
     * @callback SqliteContactDAO~RetrieveAllCallback
     * @param {Error} err - Error object containing the error message from SQLite
     * @param {Array} contacts - Array of Contacts
     */

    /**
     * Retrieves all contacts from the Database
     * @param {SqliteContactDAO~RetrieveAllCallback} [callback]
     */
    retrieveAll: function (callback) {
        var sql = 'SELECT * FROM Contact WHERE isActive=1 ORDER BY Name, Surname ASC;';
        this.all(sql, null, function (err, result) {
            var contacts = [];
            if (result && result.length) {
                for (var i = 0; i < result.length; i++) {
                    contacts.push(SqliteContactDAO.ContactFromDatabase(result[i]));
                }
            }
            if (callback) {
                callback(err, contacts);
            }
        });
    },

    /**
     * Called after retrieving a contact
     * @callback SqliteContactDAO~RetrieveCallback
     * @param {Error} err - Error object containing the error message from SQLite
     * @param {Contact} contact - Request Contact or null
     */

    /**
     * Attempts to retrieve a contact by id
     * @param {Number} id - Contact ID to retrieve
     * @param {SqliteContactDAO~RetrieveCallback} [callback]
     */
    retrieve: function (id, callback) {
        var sql = 'SELECT * FROM Contact WHERE ContactId=$id AND isActive=1 LIMIT 1 ;';

        this.all(sql, {$id: id}, function (err, result) {
            var contact = null;
            if (result.length) {
                contact = SqliteContactDAO.ContactFromDatabase(result[0]);
            }

            if (callback) {
                callback(err, contact);
            }
        });
    },


    /**
     * Attempts to retrieve a contact by name
     * @param {String} name - Contacts Given Name
     * @param {String} surname - Contacts Surname
     * @param {SqliteContactDAO~RetrieveCallback} [callback]
     */
    retrieveByName: function (name, surname, callback) {
        var sql = 'SELECT * FROM Contact WHERE Name=$name AND Surname=$surname AND isActive=1 LIMIT 1;';

        this.all(sql, {$name: name, $surname: surname}, function (err, result) {
            var contact = null;
            if (result.length) {
                contact = SqliteContactDAO.ContactFromDatabase(result[0]);
            }
            if (callback) {
                callback(err, contact);
            }
        });
    },
    /**
     * Retrieves a range of contacts
     * @param {Number} offset - Number of contacts to skip
     * @param {Number} limit - Maximum number of contacts to retrieve
     * @param {SqliteContactDAO~RetrieveAllCallback} [callback]
     */
    retrieveRange: function (offset, limit, callback) {
        var sql = 'SELECT * FROM Contact ORDER BY Name, Surname ASC LIMIT $limit OFFSET $offset;';

        this.all(sql, {$limit: limit, $offset: offset}, function (err, result) {
            var contacts = [];
            if (result.length) {
                for (var i = 0; i < result.length; i++) {
                    contacts.push(SqliteContactDAO.ContactFromDatabase(result[i]));
                }
            }
            if (callback) {
                callback(err, contacts);
            }
        });
    },

    _updateContact: function(contact, callback){
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

        this.query(sql, values, function (err) {
            if (callback) {
                callback(err);
            }
        });
    },
    /**
     * Updates a contact reference in the database.
     * @param {Contact} contact - Contact to update
     * @param {SqliteContactDAO~GeneralCallback} [callback]
     */
    update: function (contact, callback) {
        this._updateContact(contact, callback);
    },

    /**
     * Removes a contact reference from the database.
     * @param {Number} id - Contact id to remove
     * @param {SqliteContactDAO~GeneralCallback} [callback]
     */
    remove: function (id, callback) {
        var sql = 'UPDATE Contact SET isActive=0 WHERE ContactId=$id;';
        this.query(sql, {$id: id}, function (err) {

            if (callback) {
                callback(err);
            }
        });
    },


    _lastInsertedContactId: function (callback) {
        var sql = 'SELECT ContactId FROM Contact ORDER BY ContactId DESC LIMIT 1;';

        this.all(sql, null, function (err, result) {
            var id = 0;
            if (result.length) {
                id = result[0].ContactId;
            }
            if (callback) {
                callback(err, id);
            }
        });
    },

    /**
     * Called after retrieving a contact
     * @callback SqliteContactDAO~LastIdCallback
     * @param {Error} err - Error object containing the error message from SQLite
     * @param {Number} id - Last Inserted Contact id
     */

    /**
     * Retrieves the last inserted contact id
     * @param {SqliteContactDAO~LastIdCallback} [callback]
     */
    lastInsertedId: function (callback) {
        this._lastInsertedContactId(callback);
    },

    _lastInsertedContact: function (callback) {
        var sql = 'SELECT * FROM Contact ORDER BY ContactId DESC LIMIT 1;';

        this.all(sql, null, function (err, result) {
            var contact = null;
            if (result.length) {
                contact = SqliteContactDAO.ContactFromDatabase(result[0]);
            }

            if (callback) {
                callback(err, contact);
            }
        });
    },

    /**
     * Retrieves the last inserted contact
     * @param {SqliteContactDAO~RetrieveCallback} [callback]
     */
    lastInserted: function (callback) {
        this._lastInsertedContact(callback);
    }
});

/**
 * Converts a Database Row into a Contact reference
 * @param {Object} row - Sqlite Database row.
 * @returns {Contact} - Contact object.
 * @constructor
 */
SqliteContactDAO.ContactFromDatabase = function (row) {
    var c = new Contact();
    c.setId(row.ContactId);
    c.setName(row.Name);
    c.setMiddleName(row.MiddleName);
    c.setSurname(row.Surname);

    c.setCompany(row.Company);
    c.setEmail(row.Email);
    c.setPhone(row.Phone);

    c.setAddress(row.Address, row.Suburb, row.City, row.Country, row.State, row.Post);

    return c;
};

module.exports = SqliteContactDAO;