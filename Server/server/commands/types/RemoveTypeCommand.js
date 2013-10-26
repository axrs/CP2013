var Ring = require('ring');
var AbstractTypeCommand = require('./AbstractTypeCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var DeleteTypeCommand = Ring.create([AbstractTypeCommand], {
    _id: 0,
    /**
     *
     * @param {Number} id
     * @param {ITypeDAO} dao
     */
    init: function (id, dao) {
        this._id = id;
        this.$super(dao);
    },

    /**
     *
     * @param req
     * @param res
     */
    execute: function (req, res) {
        var dao = this._dao;
        if (this._id == 0) {
            StatusHelpers.status400(req, res);
        } else {
            dao.remove(this._id, function (err) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else {
                    StatusHelpers.status202(req, res);
                }
            });
        }
    }
});

module.exports = DeleteTypeCommand;