var Ring = require('ring');
var Contacts = require('./SqliteContactDAO.js');
var Users = require('./SqliteUserDAO.js');
var Types = require('./SqliteAppointmentTypeDAO.js');

var IDAOFactory = require('../IDAOFactory.js');

var SqliteDAOFactory = Ring.create(IDAOFactory, {
    init: function (databaseConnection) {
        this._db = databaseConnection;
    },
    getContactDAO: function () {
        return new Contacts(this._db);
    },
    getUserDAO: function () {
        return new Users(this._db);
    },
    getTypeDAO: function () {
        return new Types(this._db);
    }
});

module.exports = SqliteDAOFactory;
