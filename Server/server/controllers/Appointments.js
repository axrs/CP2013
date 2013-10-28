var Appointment = require('../models/Appointment.js');

var AllCommand = require('../commands/appointments/AllAppointmentsCommand.js');
var AllUserCommand = require('../commands/appointments/GetAppointmentsUserCommand.js');

var GetAvailabilitiesCommand = require('../commands/appointments/GetAvailabilities.js');
var CreateCommand = require('../commands/appointments/CreateAppointmentCommand.js');
var RemoveCommand = require('../commands/appointments/RemoveAppointmentCommand.js');
var passport = require('passport');


var DAO = require('../dao/DAO.js');

var allCMD = function (req, res) {
    if (req.user.getIsAdmin()) {
        new AllCommand(DAO.getAppointmentDAO()).execute(req, res);
    } else {
        new AllUserCommand(DAO.getAppointmentDAO()).execute(req, res);
    }
};
var allAvailableCMD = function (req, res) {
    new GetAvailabilitiesCommand(req.params.start, req.params.end, DAO.getAppointmentDAO()).execute(req, res);
};

var createCMD = function (req, res) {
    var appointment = Appointment.fromJSON(req.body);
    new CreateCommand(appointment, DAO.getAppointmentDAO()).execute(req, res);
};

var removeCMD = function (req, res) {
    new RemoveCommand(req.params.id, DAO.getAppointmentDAO()).execute(req, res);
};
server = module.exports.server = module.parent.exports.server;

/**
 * API Routing
 */
server.get('/api/appointments',
    passport.authenticate('bearer', { session: false }),
    allCMD
);

server.put('/api/appointments',
    passport.authenticate('bearer', { session: false }),
    createCMD
);
server.delete('/api/appointments/:id',
    passport.authenticate('bearer', { session: false }),
    removeCMD
);

server.get('/api/available/:start/:end',
    allAvailableCMD
);

