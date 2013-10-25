var Ring = require('ring');
var IContactDAO = require('../../dao/IContactDAO.js');
var AbstractProviderCommand = require('./AbstractProviderCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var GetProviderCommand = Ring.create([AbstractProviderCommand], {

    init: function (dao) {
        this.$super(dao);
    },
    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        this._dao.retrieveAll(function (err, results) {
            if (err) {
                StatusHelpers.status500(req, res);
            } else {
                res.writeHead(200, { 'Content-Type': 'application/json' });
                res.write(JSON.stringify(results));
                res.end();
            }
        });
    }
});

module.exports = GetProviderCommand;