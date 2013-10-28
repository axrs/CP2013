var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var sqlite = require('sqlite3');
var config = require('../../config/config.js');

var Ring = require('ring');
var Contacts = require('./SqliteContactDAO.js');
var Users = require('./SqliteUserDAO.js');
var Types = require('./SqliteAppointmentTypeDAO.js');
var Providers = require('./SqliteProviderDAO.js');
var Appointments = require('./SqliteAppointmentDAO.js');

var IDAOFactory = require('../IDAOFactory.js');

var SqliteDAOFactory = Ring.create([IDAOFactory], {
    init: function () {
        this._db = new sqlite.Database(config.db);
    },
    getContactDAO: function () {
        return new Contacts(this._db);
    },
    getUserDAO: function () {
        return new Users(this._db);
    },
    getAppointmentTypeDAO: function () {
        return new Types(this._db);
    },
    getProviderDAO: function () {
        return new Providers(this._db);
    },
    getAppointmentDAO: function () {
        return new Appointments(this._db);
    }
});

module.exports = SqliteDAOFactory;
