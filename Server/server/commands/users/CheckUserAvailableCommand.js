var Ring = require('ring');
var User = require('../../models/User.js');
var AbstractUserCommand = require('./AbstractUserCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var CheckUserAvailableCommand = Ring.create([AbstractUserCommand], {
    _username: "",
    init: function (username, dao) {
        this._username = username;
        this.$super(dao);
    },

    execute: function (req, res) {
        var dao = this._dao;
        dao.retrieveByUserName(this._username, function (err, result) {
            if (err) {
                StatusHelpers.status500(req, res);
            } else if (!result) {
                StatusHelpers.status200(req, res);
            } else {
                StatusHelpers.status409(req, res);
            }
        });
    }
});

module.exports = CheckUserAvailableCommand;