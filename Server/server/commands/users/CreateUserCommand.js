var Ring = require('ring');
var User = require('../../models/User.js');
var AbstractUserCommand = require('./AbstractUserCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var CreateUserCommand = Ring.create([AbstractUserCommand], {
    _user: null,
    /**
     * @param {User} user
     * @param {IContactDAO} contactDAO
     */
    init: function (user, contactDAO) {
        this._user = user;
        this.$super(contactDAO);
    },
    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        var user = this._user;
        var dao = this._dao;

        if (!user.isValid() || user.getId() > 0) {
            StatusHelpers.status400(req, res);
        } else {
            var id = user.getId();
            if (user.getStrategy() != 'local') {
                id = user.getStrategyId();
            }
            dao.retrieveById(id, user.getStrategy(), function (err, result) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else if (result) {
                    StatusHelpers.status409(req, res);
                } else {
                    dao.create(user, function (err, result) {
                        if (err) {
                            StatusHelpers.status500(req, res);
                        } else {
                            dao.lastInserted(function (err, result) {
                                res.writeHead(201, { 'Content-Type': 'application/json' });
                                user = result;
                                res.write(JSON.stringify(user));
                                res.end();
                            });
                        }
                    });
                }
            });
        }
    }
});

module.exports = CreateUserCommand;