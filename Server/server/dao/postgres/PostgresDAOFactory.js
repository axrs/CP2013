var Interface = projectRequire('interfaces/Interface'),
    SqliteDAO = projectRequire('interfaces/SqliteDAO'),
    SqliteContactDAO = require('./../sqlite/SqliteContactDAO'),
    DAOFactory = projectRequire('interfaces/DAOFactory');

function PostgresDAOFactory(connectionString) { // extends DAOFactory
    var _connection = databaseConnection;
    this.getContactDAO = function () {
        return new SqliteContactDAO(_connection);
    };
    this.getUserDAO = function () {
        throw new Error('getUserDAO: Not Implemented.');
    };
};

module.exports = PostgresDAOFactory;
