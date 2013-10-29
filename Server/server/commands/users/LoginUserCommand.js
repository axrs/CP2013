var Ring = require('ring');
var User = require('../../models/User.js');
var AbstractUserCommand = require('./AbstractUserCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var LoginUserCommand = Ring.create([AbstractUserCommand], {
    _user: null,
    _password: "",
    init: function (user, password, dao) {
        this._password = password;
        this._user = user;
        this.$super(dao);
    },

    execute: function (req, res) {
        var user = this._user;
        var dao = this._dao;
        var password = this._password;
        dao.retrieveByUserName(user.getUserName(), function (err, result) {
            if (err) {
                StatusHelpers.status500(req, res);
            } else if (!result) {
                StatusHelpers.status404(req, res);
            } else {
                if (result.validatePassword(password)) {
                    res.writeHead(202, { 'Content-Type': 'application/json' });
                    user = result;
                    res.write(JSON.stringify(result));
                    res.end();
                } else {
                    StatusHelpers.status409(req, res);
                }
            }
        });
    }
});

module.exports = LoginUserCommand;