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

        var providerToUpdate = this._provider;
        var data = providerToUpdate.toJSON();

        var dao = this._dao;
        dao.retrieveById(providerToUpdate.getId(), function (err, result) {
            if (err) {
                StatusHelpers.status500(req, res);
            } else {
                var updatedContact = Provider.fromJSON(
                    Utilities.mergeObjectProperties([
                        result.toJSON(),
                        data
                    ])
                );
                if (!providerToUpdate.isValid() || providerToUpdate.getId() <= 0) {
                    StatusHelpers.status400(req, res);
                } else {
                    dao.update(updatedContact, function (err) {
                        if (err) {
                            StatusHelpers.status500(req, res);
                        } else {
                            res.writeHead(202, { 'Content-Type': 'application/json' });
                            res.write(JSON.stringify(updatedContact));
                            res.end();

                            var id = providerToUpdate.getId();
                            dao.removeProviderHours(id, function (err, result) {
                                dao.updateProviderHours(id, providerToUpdate.getHours(), null);
                            });
                        }
                    });
                }
            }
        });
    }
});

module.exports = UpdateProviderCommand;