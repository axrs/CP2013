var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractAppointmentCommand = require('./AbstractAppointmentCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var RemoveAppointmentCommand = Ring.create([AbstractAppointmentCommand], {

    _id: 0,

    init: function (id, dao) {
        this.$super(dao);
        this._id = id;
    },

    execute: function (req, res) {
        var id = this._id;
        if (id > 0) {
            this._dao.remove(req.params.id, function (err, result) {
                if (err) StatusHelpers.status500(req, res);
                else {
                    StatusHelpers.status202(req, res);
                }
            });
        } else {
            StatusHelpers.status400(req, res);
        }
    }
});

module.exports = RemoveAppointmentCommand;