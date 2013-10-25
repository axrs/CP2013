var Ring = require('ring');
var IContactDAO = require('../../dao/IContactDAO.js');
var AbstractProviderCommand = require('./AbstractProviderCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var GetProviderHoursCommand = Ring.create([AbstractProviderCommand], {
    _id: 0,

    init: function (id, dao) {
        this.$super(dao);
        this._id = id;
    },

    execute: function (req, res) {
        this._dao.retrieveProviderHours(this._id, function (err, results) {
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

module.exports = GetProviderHoursCommand;