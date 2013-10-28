var Ring = require('ring');
var Appointment = require('../../models/Appointment.js');
var AbstractAppointmentCommand = require('./AbstractAppointmentCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var CreateAppointmentCommand = Ring.create([AbstractAppointmentCommand], {
    _appointment: null,

    init: function (appointment, dao) {
        this._appointment = appointment;
        this.$super(dao);
    },

    execute: function (req, res) {
        var dao = this._dao;
        var appointment = this._appointment;
        if (!appointment.isValid() || appointment.getId() > 0) {
            StatusHelpers.status400(req, res);
        } else {
            dao.create(appointment, function (err) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else {
                    dao.lastInserted(function (err, result) {
                        res.writeHead(201, { 'Content-Type': 'application/json' });
                        appointment = result;
                        res.write(JSON.stringify(result));
                        res.end();
                    });
                }
            });
        }
    }
});

module.exports = CreateAppointmentCommand;