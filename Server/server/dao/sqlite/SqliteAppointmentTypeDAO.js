var Ring = require('ring');
var ICRUDDAO = require('../ICRUDDAO.js');
var SqliteHelper = require('./SqliteHelper.js');
var Type = require('../../models/AppointmentType.js');

var SqliteAppointmentTypeDAO = Ring.create([ICRUDDAO], {
    _db: null,
    /**
     * Constructor
     * @param  databaseConnection
     */
    init: function (databaseConnection) {
        this._db = databaseConnection;
    },

    create: function (type, callback) {
        var sql = '' +
            'INSERT INTO Appointment_Type ' +
            '(Description, Duration, isAllDay, isActive) ' +
            'VALUES ' +
            '($description, $duration, $isAllDay, $isActive);';

        var values = {
            $description: type.getDescription(),
            $duration: type.getDuration(),
            $isAllDay: type.getIsAllDay(),
            $isActive: type.getIsActive()
        };

        var helper = new SqliteHelper(this._db);

        helper.query(sql, values, function (err) {
            if (callback) {
                callback(err);
            }
        });
    },
    retrieveAll: function (callback) {
        var sql = 'SELECT * FROM Appointment_Type WHERE isActive=1 ORDER BY Description;';

        var helper = new SqliteHelper(this._db);

        helper.all(sql, null, function (err, result) {
            var types = [];

            if (result && result.length) {
                for (var i = 0; i < result.length; i++) {
                    types.push(SqliteAppointmentTypeDAO.TypeFromDatabase(result[i]));
                }
            }
            if (callback) {
                callback(err, types);
            }
        });
    },
    retrieveById: function (id, callback) {
        var sql = 'SELECT * FROM Appointment_Type WHERE TypeId=$id AND isActive=1 LIMIT 1;';

        var helper = new SqliteHelper(this._db);
        helper.all(sql, {$id: id}, function (err, result) {
            var type = null;
            if (result.length) {
                type = SqliteAppointmentTypeDAO.TypeFromDatabase(result[0]);
            }

            if (callback) {
                callback(err, type);
            }
        });
    },
    update: function (type, callback) {
        var sql = '' +
            'UPDATE Appointment_Type ' +
            'SET Description=$description, Duration=$duration, isAllDay=$isAllDay, isActive=$active ' +
            'WHERE TypeId=$id;';

        var values = {
            $description: type.getDescription(),
            $duration: type.getDuration(),
            $isAllDay: type.getIsAllDay(),
            $isActive: type.getIsActive(),
            $id: type.getId()
        };
        var helper = new SqliteHelper(this._db);

        helper.query(sql, values, function (err) {
            if (callback) {
                callback(err);
            }
        });
    },
    remove: function (contact, callback) {
        throw new Error('Method Not Implemented.');
    },
    lastInsertedId: function (callback) {
        throw new Error('Method Not Implemented.');
    },
    lastInserted: function (callback) {
        throw new Error('Method Not Implemented.');
    }
});

SqliteAppointmentTypeDAO.TypeFromDatabase = function (row) {
    var t = new Type();
    t.setId(row.TypeId);
    t.setDescription(row.Description);
    t.setDuration(row.Duration);
    t.setIsAllDay(row.isAllDay);
    t.setIsActive(row.isActive);
    return t;
};

module.exports = SqliteAppointmentTypeDAO;