var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractConcreteCommand = require('./AbstractContactCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var CreateContactCommand = Ring.create([AbstractConcreteCommand], {
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
        var contact = Contact.fromJSON(req.body);

        if (!contact.isValid() || contact.getId() > 0) {
            StatusHelpers.status400(req, res);
        } else {
            this._dao.retrieveByName(contact.getName(), contact.getSurname(), function (err, result) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else if (result) {
                    StatusHelpers.status409(req, res);
                } else {
                    this._dao.create(contact, function (err, result) {
                        if (err) {
                            StatusHelpers.status500(req, res);
                        } else {
                            this._dao.lastInserted(function (err, result) {
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