var Ring = require('ring');
var Appointment = require('../../models/AppointmentType.js');
var SqliteHelper = require('./SqliteHelper.js');

var SqliteAppointmentDAO = Ring.create([SqliteHelper], {
    init: function (databaseConnection) {
        this.$super(databaseConnection);
    },

    create: function (appointment, callback) {
        var database = this._db;
        var sql = '' +
            'INSERT INTO Appointment ' +
            '(TypeId, ContactId, ProviderId, Date, Time) ' +
            'VALUES ' +
            '($type, $contact, $provider, $date, $time);';

        var values = {
            $type: appointment.getTypeId(),
            $contact: appointment.getContactId(),
            $provider: appointment.getProviderId(),
            $date: appointment.getDate(),
            $time: appointment.getTime()
        };

        database.query(sql, values, function (err) {
            if (callback) {
                callback(err);
            }
        });
    },
    retrieveAll: function (callback) {
        var sql = 'SELECT * FROM Appointment;';
        this.all(sql, null, function (err, result) {
            var appointments = [];
            if (result && result.length) {
                for (var i = 0; i < result.length; i++) {
                    appointments.push(SqliteAppointmentDAO.AppointmentFromDatabase(result[i]));
                }
            }
            if (callback) {
                callback(err, appointments);
            }
        });
    },
    retrieveById: function (id, callback) {
        var sql = 'SELECT * FROM Appointment WHERE AppointmentId=$id LIMIT 1;';
        this.all(sql, {$id: id}, function (err, result) {
            var appointment = null;
            if (result && result.length) {
                appointment = SqliteAppointmentDAO.AppointmentFromDatabase(result);
            }
            if (callback) {
                callback(err, appointment);
            }
        });
    },
    remove: function (id, callback) {
        var sql = 'DELETE FROM Appointment WHERE AppointmentId=$id;';
        this.all(sql, {$id: id}, function (err) {
            if (callback) {
                callback(err);
            }
        });
    }

});

SqliteAppointmentDAO.AppointmentFromDatabase = function (row) {
    var appointment = new Appointment();

    appointment.setId(row.AppointmentId);
    appointment.setTypeId(row.TypeId);
    appointment.setContactId(row.ContactId);
    appointment.setProviderId(row.ProviderId);
    appointment.setDate(row.Date);
    appointment.setTime(row.Time);

    return appointment;
};

module.exports = SqliteAppointmentDAO;