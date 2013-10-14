var Ring = require('ring');
var LogDispatcher = require('../../utilities/LogEventDispatcher.js');

var SqliteHelper = Ring.create([], {
    _db: null,
    /**
     * Constructor
     * @param  databaseConnection
     */
    init: function (databaseConnection) {
        this._db = databaseConnection;
    },
    /**
     * Runs the SQL query with the specified parameters and calls the callback afterwards. It does not retrieve any
     * result data.
     * @param {string} sql SQL (prepared optional) statement
     * @param {Array} values prepared values to substitute into the array, or null
     * @param callback
     */
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
    /**
     * Runs the SQL query with the specified parameters and calls the callback with all result rows afterwards.
     * @param {string} sql SQL (prepared optional) statement
     * @param {Array} values prepared values to substitute into the array, or null
     * @param callback
     */
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
    }
});


module.exports = SqliteHelper;