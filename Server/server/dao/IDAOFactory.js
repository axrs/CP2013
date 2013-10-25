var Ring = require('ring');

var IDAOFactory = Ring.create({
    getContactDAO: function () {
        throw new Error('Method Not Implemented.');
    },
    getUserDAO: function () {
        throw new Error('Method Not Implemented.');
    },
    getAppointmentTypeDAO: function () {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = IDAOFactory;