var Ring = require('ring');
var SqliteContactDAO = require('./SqliteContactDAO.js');
var SqliteUserDAO = require('./SqliteUserDAO.js');

var IDAOFactory = require('../IDAOFactory.js');

var SqliteDAOFactory = Ring.create([IDAOFactory], {
    _db: null,
    init: function (databaseConnection) {
        this._db = databaseConnection;
    },
    getContactDAO: function () {
        return new SqliteContactDAO(this._db);
    },
    getUserDAO: function () {
        return new SqliteUserDAO(this._db);
    }
});

module.exports = SqliteDAOFactory;
