var Interface = projectRequire('interfaces/Interface'),
    SqliteDAO = projectRequire('interfaces/SqliteDAO'),
    SqliteContactDAO = require('./SqliteContactDAO'),
    DAOFactory = projectRequire('interfaces/DAOFactory');

function SqliteDAOFactory(databaseConnection) { // extends DAOFactory
    Interface.ensureImplements(databaseConnection, SqliteDAO);
    var _connection = databaseConnection;
    this.getContactDAO = function () {
        return new SqliteContactDAO(_connection);
    };
    this.getUserDAO = function () {
        throw new Error('getUserDAO: Not Implemented.');
    };
};

module.exports = SqliteDAOFactory;
