var Appointment = require('../models/Appointment.js');

var AllCommand = require('../commands/appointments/AllAppointmentsCommand.js');
var GetAvailabilitiesCommand = require('../commands/appointments/GetAvailabilities.js');
var CreateCommand = require('../commands/appointments/CreateAppointmentCommand.js');

var DAO = require('../dao/DAO.js');

var allCMD = function (req, res) {
    new AllCommand(DAO.getAppointmentDAO()).execute(req, res);
};
var allAvailableCMD = function (req, res) {
    new GetAvailabilitiesCommand(req.params.start, req.params.end, DAO.getAppointmentDAO()).execute(req, res);
};

var createCMD = function (req, res) {
    var Appointment = Appointment.fromJSON(req.body);
    new CreateCommand(Appointment, DAO.getAppointmentDAO()).execute(req, res);
};

server = module.exports.server = module.parent.exports.server;

/**
 * API Routing
 */
server.get('/api/appointments',
    allCMD
);

server.put('/api/appointments',
    createCMD
);

server.get('/api/available/:start/:end',
    allAvailableCMD
);
