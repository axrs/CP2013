var Type = require('../models/AppointmentType.js');
var AllCommand = require('../commands/types/AllTypesCommand.js');
var CreateCommand = require('../commands/types/CreateTypeCommand.js');
var UpdateCommand = require('../commands/types/UpdateTypeCommand.js');
var RemoveCommand = require('../commands/types/RemoveTypeCommand.js');

var DAO = require('../dao/DAO.js');

var allCMD = function (req, res) {
    new AllCommand(DAO.getAppointmentTypeDAO()).execute(req, res);
};
var createCMD = function (req, res) {
    var type = Type.fromJSON(req.body);
    new CreateCommand(type, DAO.getAppointmentTypeDAO()).execute(req, res);
};

var updateCMD = function (req, res) {
    var type = Type.fromJSON(req.body);
    new UpdateCommand(type, DAO.getAppointmentTypeDAO()).execute(req, res);
};


var removeCMD = function (req, res) {
    new RemoveCommand(req.params.id, DAO.getAppointmentTypeDAO()).execute(req, res);
};
server = module.exports.server = module.parent.exports.server;

/**
 * API Routing
 */
server.get('/api/types',
    server.logger,
    allCMD
);

server.put('/api/types',
    server.logger,
    createCMD
);

server.put('/api/types/:id',
    server.logger,
    updateCMD
);
server.delete('/api/types/:id',
    server.logger,
    removeCMD
);
