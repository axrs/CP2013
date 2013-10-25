var Ring = require('ring');
var AppointmentType = require('../../models/AppointmentType.js');
var AbstractTypeCommand = require('./AbstractTypeCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var CreateTypeCommand = Ring.create([AbstractTypeCommand], {
    _type: null,
    /**
     *
     * @param {AppointmentType} type
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
        if (type.getId() > 0) {
            StatusHelpers.status400(req, res);
        } else {
            dao.create(type, function (err) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else {
                    res.writeHead(201, { 'Content-Type': 'application/json' });
                    res.write(JSON.stringify(type));
                    res.end();
                }
            });
        }
    }
});

module.exports = CreateTypeCommand;