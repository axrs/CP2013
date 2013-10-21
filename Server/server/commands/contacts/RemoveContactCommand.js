var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractConcreteCommand = require('./AbstractContactCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var RemoveContactCommand = Ring.create([AbstractConcreteCommand], {
    _id: 0,
    /**
     *
     * @param {Number} id
     * @param {IContactDAO} contactDAO
     */
    init: function (id, contactDAO) {
        this.$super(contactDAO);
    },
    /**
     *
     * @param req
     * @param res
     */
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

module.exports = RemoveContactCommand;