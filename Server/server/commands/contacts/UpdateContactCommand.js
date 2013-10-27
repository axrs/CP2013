var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractConcreteCommand = require('./AbstractContactCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var UpdateContactCommand = Ring.create([AbstractConcreteCommand], {
    _contact: null,

    /**
     * @param {Contact} contact
     * @param {IContactDAO} contactDAO
     */
    init: function (contact, contactDAO) {
        this._contact = contact;
        this.$super(contactDAO);
    },

    execute: function (req, res) {

        var contact = this._contact;
        var dao = this._dao;
        dao.retrieveById(contact.getId(), function (err, result) {
            var updatedContact = Contact.fromJSON(
                Utilities.mergeObjectProperties([
                    result.toJSON(),
                    contact.toJSON()
                ])
            );

            if (err) {
                StatusHelpers.status500(req, res);
            } else {
                if (!contact.isValid() || contact.getId() <= 0) {
                    StatusHelpers.status400(req, res);
                } else {
                    dao.update(updatedContact, function (err) {
                        if (err) {
                            StatusHelpers.status500(req, res);
                        } else {
                            res.writeHead(202, { 'Content-Type': 'application/json' });
                            res.write(JSON.stringify(contact));
                            res.end();
                        }
                    });
                }
            }
        });
    }
});

module.exports = UpdateContactCommand;