var Ring = require('ring');
var IContactDAO = require('../../dao/IContactDAO.js');
var AbstractConcreteCommand = require('./AbstractContactCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var AllContactsCommand = Ring.create([AbstractConcreteCommand], {
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

module.exports = AllContactsCommand;