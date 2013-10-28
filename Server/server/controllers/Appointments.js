var Appointment = require('../models/Appointment.js');

var AllCommand = require('../commands/appointments/AllAppointmentsCommand.js');
var GetAvailabilitiesCommand = require('../commands/appointments/GetAvailabilities.js');

var DAO = require('../dao/DAO.js');

var allCMD = function (req, res) {
    new AllCommand(DAO.getAppointmentDAO()).execute(req, res);
};
var getAvailabile = function (req, res) {
    //var type = Type.fromJSON(req.body);
    new GetAvailabilitiesCommand(req.params.start, req.params.end, DAO.getAppointmentDAO()).execute(req, res);
};

server = module.exports.server = module.parent.exports.server;

/**
 * API Routing
 */
server.get('/api/appointments',
    allCMD
);

server.get('/api/available/:start/:end',
    getAvailabile
);
