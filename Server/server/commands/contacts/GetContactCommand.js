var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractConcreteCommand = require('./AbstractContactCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var GetContactCommand = Ring.create([AbstractConcreteCommand], {
    _id: 0,


    init: function (id, contactDAO) {
        this._id = id;
        this.$super(contactDAO);
    },

    execute: function (req, res) {

        var dao = this._dao;
        dao.retrieveById(this._id, function (err, result) {

            if (err) {
                StatusHelpers.status500(req, res);
            } else {
                res.writeHead(200, { 'Content-Type': 'application/json' });
                res.write(JSON.stringify(result));
                res.end();
            }
        });
    }
});

module.exports = GetContactCommand;