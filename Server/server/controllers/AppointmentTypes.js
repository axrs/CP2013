var Type = require('../models/AppointmentType.js');
var AllCommand = require('../commands/types/AllTypesCommand.js');
var CreateCommand = require('../commands/types/CreateTypeCommand.js');
var UpdateCommand = require('../commands/types/UpdateTypeCommand.js');
var RemoveCommand = require('../commands/types/RemoveTypeCommand.js');
var GetCommand = require('../commands/types/GetTypeCommand.js');
var passport = require('passport');

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

var getCMD = function (req, res) {
    new GetCommand(req.params.id, DAO.getAppointmentTypeDAO()).execute(req, res);
};

var removeCMD = function (req, res) {
    new RemoveCommand(req.params.id, DAO.getAppointmentTypeDAO()).execute(req, res);
};

server = module.exports.server = module.parent.exports.server;

/**
 * API Routing
 */
server.get('/api/types',
    allCMD
);
server.get('/api/types/:id',
    getCMD
);

server.put('/api/types',
    passport.authenticate('bearer', { session: false }),
    server.requiresAdmin,
    createCMD
);

server.put('/api/types/:id',
    passport.authenticate('bearer', { session: false }),
    server.requiresAdmin,
    updateCMD
);
server.delete('/api/types/:id',
    passport.authenticate('bearer', { session: false }),
    server.requiresAdmin,
    removeCMD
);


if (server.env === "development") {

    var testRemove = function (req, res) {
        new RemoveCommand(1, DAO.getAppointmentTypeDAO()).execute(req, res);
    };

    var testUpdate = function (req, res) {
        var type = new Type();
        type.setId(1);
        type.setDescription("Testing Creation - Modified");
        type.setDuration("03:45");
        new UpdateCommand(type, DAO.getAppointmentTypeDAO()).execute(req, res);
    };

    var testCreate = function (req, res) {
        var type = new Type();
        type.setDescription("Testing Creation");
        type.setDuration("00:45");

        new CreateCommand(type, DAO.getAppointmentTypeDAO()).execute(req, res);
    };

    server.get('/api/test/types/create',
        testCreate
    );
    server.get('/api/test/types/update',
        testUpdate
    );

    server.get('/api/test/types/delete',
        testRemove
    );
}
