var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');
var Provider = require('../models/Provider.js');
var AllCommand = require('../commands/provider/AllProvidersCommand.js');
var CreateCommand = require('../commands/provider/CreateProviderCommand.js');
var GetCommand = require('../commands/provider/GetProviderCommand.js');
var UpdateCommand = require('../commands/provider/UpdateProviderCommand.js');
var RemoveCommand = require('../commands/provider/RemoveProviderCommand.js');

var passport = require('passport');

var DAO = require('../dao/DAO.js');

var allCMD = function (req, res) {
    new AllCommand(DAO.getProviderDAO()).execute(req, res);
};
var getCMD = function (req, res) {
    new GetCommand(req.params.id, DAO.getProviderDAO()).execute(req, res);
};

var createCMD = function (req, res) {
    var provider = Provider.fromJSON(req.body);
    new CreateCommand(provider, DAO.getProviderDAO()).execute(req, res);
};
var updateCMD = function (req, res) {
    var provider = Provider.fromJSON(req.body);
    new UpdateCommand(provider, DAO.getProviderDAO()).execute(req, res);
};
var removeCMD = function (req, res) {
    new RemoveCommand(req.params.id, DAO.getContactDAO()).execute(req, res);
};

server = module.exports.server = module.parent.exports.server;

server.get('/api/providers',
    allCMD
);
server.get('/api/providers/:id',
    getCMD
);
server.put('/api/providers',
    createCMD
);
server.delete('/api/providers/:id',
    removeCMD
);
server.put('/api/providers/:id',
    updateCMD
);