var Ring = require('ring');
var Provider = require('../../models/Provider.js');
var AbstractProviderCommand = require('./AbstractProviderCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var UpdateProviderCommand = Ring.create([AbstractProviderCommand], {
    _provider: null,

    init: function (provider, contactDAO) {
        this._provider = provider;
        this.$super(contactDAO);
    },

    execute: function (req, res) {

        var provider = this._provider;
        var dao = this._dao;
        dao.retrieveById(provider.getId(), function (err, result) {
            var updatedContact = Provider.fromJSON(
                Utilities.mergeObjectProperties([
                    result.toJSON(),
                    provider.toJSON()
                ])
            );

            if (err) {
                StatusHelpers.status500(req, res);
            } else {
                if (!provider.isValid() || provider.getId() <= 0) {
                    StatusHelpers.status400(req, res);
                } else {
                    dao.update(updatedContact, function (err) {
                        if (err) {
                            StatusHelpers.status500(req, res);
                        } else {
                            res.writeHead(202, { 'Content-Type': 'application/json' });
                            res.write(JSON.stringify(provider));
                            res.end();
                        }
                    });
                }
            }
        });
    }
});

module.exports = UpdateProviderCommand;