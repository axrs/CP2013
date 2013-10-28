var Ring = require('ring');
var IAppointmentDAO = require('../../dao/IAppointmentDAO.js');
var AbstractDAOCommand = require('../AbstractDAOCommand.js');

var AbstractAppointmentCommand = Ring.create([AbstractDAOCommand], {

    init: function (dao) {
        if (!Ring.instance(dao, IAppointmentDAO)) {
            throw new Error('Invalid DAO Passed.');
        }
        this.$super(dao);
    }
});

module.exports = AbstractAppointmentCommand;