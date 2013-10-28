var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractAppointmentCommand = require('./AbstractAppointmentCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var AllAvailabilitiesCommand = Ring.create([AbstractAppointmentCommand], {
    _start: "",
    _end: "",

    init: function (start, end, dao) {
        this._start = start;
        this._end = end;
        this.$super(dao);
    },

    execute: function (req, res) {
        var dao = this._dao;
        dao.retrieveAllAvailabilities(this._start, this._end, function (err, result) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else {
                    res.writeHead(200, { 'Content-Type': 'application/json' });
                    res.write(JSON.stringify(result));
                    res.end();
                }
            }
        );
    }
});

module.exports = AllAvailabilitiesCommand;