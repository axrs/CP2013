var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractConcreteCommand = require('./AbstractContactCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var CreateContactCommand = Ring.create([AbstractConcreteCommand], {
    _contact: null,
    /**
     *
     * @param {Contact} contact
     * @param {IContactDAO} contactDAO
     */
    init: function (contact, contactDAO) {
        this._contact = contact;
        this.$super(contactDAO);
    },

    /**
     *
     * @param req
     * @param res
     */
    execute: function (req, res) {
        var dao = this._dao;
        var contact = this._contact;
        if (!contact.isValid() || contact.getId() > 0) {
            StatusHelpers.status400(req, res);
        } else {
            dao.retrieveByName(contact.getName(), contact.getSurname(), function (err, result) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else if (result) {
                    StatusHelpers.status409(req, res);
                } else {
                    dao.create(contact, function (err, result) {
                        if (err) {
                            StatusHelpers.status500(req, res);
                        } else {
                            dao.lastInserted(function (err, result) {
                                res.writeHead(201, { 'Content-Type': 'application/json' });
                                contact = result;
                                res.write(JSON.stringify(contact));
                                res.end();
                            });
                        }
                    });
                }
            });
        }
    }
});

module.exports = CreateContactCommand;