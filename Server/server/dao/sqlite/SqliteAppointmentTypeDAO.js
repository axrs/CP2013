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
        throw new Error('Method Not Implemented.');
    },
    retrieveById: function (id, callback) {
        throw new Error('Method Not Implemented.');
    },
    update: function (contact, callback) {
        throw new Error('Method Not Implemented.');
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