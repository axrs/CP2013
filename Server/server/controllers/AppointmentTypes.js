var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var sqlite = require('sqlite3');
var config = require('../config/config.js');
var database = new sqlite.Database(config.db);
var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');
var Type = require('../models/AppointmentType.js');
var AllCommand = require('../commands/types/AllTypesCommand.js');
var CreateCommand = require('../commands/types/CreateTypeCommand.js');
var dao = new DAOFactory(database).getTypeDAO();

var allCMD = function (req, res) {
    new AllCommand(dao).execute(req, res);
};
var createCMD = function (req, res) {
    var type = Type.fromJSON(req.body);
    new CreateCommand(type, dao).execute(req, res);
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