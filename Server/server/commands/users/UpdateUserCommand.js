var Ring = require('ring');
var User = require('../../models/User.js');
var AbstractUserCommand = require('./AbstractUserCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var UpdateUserCommand = Ring.create([AbstractUserCommand], {
    _user: null,

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

        dao.retrieveById(user.getId(), 'local', function (err, result) {

            var updatedUser = User.fromJSON(
                Utilities.mergeObjectProperties([
                    result.toJSON(),
                    user.toJSON()
                ])
            );

            if (err) {
                StatusHelpers.status500(req, res);
            } else {
                if (!updatedUser.isValid() || updatedUser.getId() == 0) {
                    StatusHelpers.status400(req, res);
                } else {
                    dao.update(updatedUser, function (err) {
                        if (err) {
                            StatusHelpers.status500(req, res);
                        } else {
                            res.writeHead(202, { 'Content-Type': 'application/json' });
                            res.write(JSON.stringify(updatedUser));
                            res.end();
                        }
                    });
                }
            }
        });
    }
});

module.exports = UpdateUserCommand;