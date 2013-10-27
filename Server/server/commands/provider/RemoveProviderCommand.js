var Ring = require('ring');
var IContactDAO = require('../../dao/IContactDAO.js');
var AbstractProviderCommand = require('./AbstractProviderCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var GetProviderCommand = Ring.create([AbstractProviderCommand], {

    _id: 0,

    init: function (id, dao) {
        this._id = id;
        this.$super(dao);
    },
    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        var id = this._id;
        this._dao.remove(id, function (err, results) {
            if (err) {
                StatusHelpers.status500(req, res);
            } else {
                res.writeHead(202, { 'Content-Type': 'application/json' });
                res.end();
            }
        });
    }
});

module.exports = GetProviderCommand;