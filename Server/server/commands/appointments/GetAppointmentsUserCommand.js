var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractAppointmentCommand = require('./AbstractAppointmentCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var AllAppointmentsCommand = Ring.create([AbstractAppointmentCommand], {

    init: function (dao) {
        this.$super(dao);
    },

    execute: function (req, res) {
        var dao = this._dao;
        dao.retrieveForContact(req.user.getContactId(), function (err, result) {
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

module.exports = AllAppointmentsCommand;