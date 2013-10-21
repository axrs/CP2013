var Ring = require('ring');
var Contact = require('../../models/Contact.js');
var AbstractConcreteCommand = require('./AbstractContactCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var UpdateContactCommand = Ring.create([AbstractConcreteCommand], {
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
        var contact = new Contact();

        this._dao.retrieve(req.params.id, function (err, result) {
            if (err) {
                StatusHelpers.status500(req, res);
            } else {
                contact = Contact.fromJSON(req.body);

                if (!contact.isValid() || contact.getId() <= 0) {
                    StatusHelpers.status400(req, res);
                } else {
                    this._dao.update(contact, function (err, result) {
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