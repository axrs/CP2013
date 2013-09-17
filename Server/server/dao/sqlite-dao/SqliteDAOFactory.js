var Interface = require('./../../interfaces/Interface'),
    SqliteDAO = require('./../../interfaces/SqliteDAO'),
    SqliteContactDAO = require('./SqliteContactDAO'),
    DAOFactory = require('./../DaoFactory');

function SqliteDAOFactory(databaseConnection) {
    Interface.ensureImplements(databaseConnection, SqliteDAO);
    var _connection = databaseConnection;
    this.getContactDAO = function () {
        return new SqliteContactDAO(_connection);
    }
}

function extend(subClass, superClass) {
    var F = function () {
    };
    F.prototype = superClass.prototype;
    subClass.prototype = new F();
    subClass.prototype.constructor = subClass;
}

extend(SqliteDAOFactory, DAOFactory);

module.exports = SqliteDAOFactory;
