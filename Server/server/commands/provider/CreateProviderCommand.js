var Ring = require('ring');
var IContactDAO = require('../../dao/IContactDAO.js');
var AbstractProviderCommand = require('./AbstractProviderCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Provider = require('../../models/Provider.js');
var Utilities = require('../../utilities/Utilities.js');

var CreateProviderCommand = Ring.create([AbstractProviderCommand], {
    _provider: null,

    init: function (provider, dao) {
        this._provider = provider;
        this.$super(dao);
    },
    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        var provider = this._provider;
        var dao = this._dao;
        if (!provider.isValid() || provider.getId() > 0) {
            StatusHelpers.status400(req, res);
        } else {
            dao.retrieveByName(provider.getName(), provider.getSurname(), function (err, result) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else if (result) {
                    StatusHelpers.status409(req, res);
                } else {
                    dao.create(provider, function (err) {
                        if (err) {
                            StatusHelpers.status500(req, res);
                        } else {
                            dao.lastInserted(function (err, result) {
                                res.writeHead(201, { 'Content-Type': 'application/json' });
                                res.write(JSON.stringify(result));
                                res.end();

                                var id = result.getId();
                                dao.removeProviderHours(id, function (err, result) {
                                    dao.updateProviderHours(id, provider.getHours(), null);
                                });
                            });
                        }
                    });
                }
            });
        }
    }
});

module.exports = CreateProviderCommand;