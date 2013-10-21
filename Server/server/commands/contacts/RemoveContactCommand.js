var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractConcreteCommand = require('./AbstractContactCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var RemoveContactCommand = Ring.create([AbstractConcreteCommand], {
    /**
     *
     * @param {IContactDAO} contactDAO
     */
    init: function (contactDAO) {
        this.$super(contactDAO);
    },
    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        if (req.params.id > 0) {
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

module.exports = RemoveContactCommand;