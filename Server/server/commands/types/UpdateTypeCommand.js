var Ring = require('ring');
var AbstractTypeCommand = require('./AbstractTypeCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');
var Utilities = require('../../utilities/Utilities.js');

var UpdateTypeCommand = Ring.create([AbstractTypeCommand], {
    _type: null,
    /**
     *
     * @param {Appointment} type
     * @param {ITypeDAO} dao
     */
    init: function (type, dao) {
        this._type = type;
        this.$super(dao);
    },

    /**
     *
     * @param req
     * @param res
     */
    execute: function (req, res) {
        var dao = this._dao;
        var type = this._type;
        if (type.getId() == 0 || !Utilities.isStringAndNotEmpty(type.getDescription)) {
            StatusHelpers.status400(req, res);
        } else {
            dao.update(type, function (err) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else {
                    res.writeHead(202, { 'Content-Type': 'application/json' });
                    res.write(JSON.stringify(type));
                    res.end();
                }
            });
        }
    }
});

module.exports = UpdateTypeCommand;