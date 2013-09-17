var Interface = projectRequire('interfaces/Interface'),
    SqliteDAO = projectRequire('interfaces/SqliteDAO'),
    SqliteContactDAO = require('./SqliteContactDAO'),
    DAOFactory = projectRequire('dao/DaoFactory');

function extend(subClass, superClass) {
    var F = function () {
    };
    F.prototype = superClass.prototype;
    subClass.prototype = new F();
    subClass.prototype.constructor = subClass;
}

extend(SqliteDAOFactory, DAOFactory);

function SqliteDAOFactory(databaseConnection) {
    Interface.ensureImplements(databaseConnection, SqliteDAO);
    var _connection = databaseConnection;
    this.getContactDAO = function () {
        return new SqliteContactDAO(_connection);
    }
};

module.exports = SqliteDAOFactory;
