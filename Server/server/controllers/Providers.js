var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');
var Provider = require('../models/Provider.js');
var ProviderHours = require('../models/ProviderHours.js');
var AllCommand = require('../commands/provider/AllProvidersCommand.js');
var CreateCommand = require('../commands/provider/CreateProviderCommand.js');
var GetCommand = require('../commands/provider/GetProviderCommand.js');
var passport = require('passport');

var DAO = require('../dao/DAO.js');

var allCMD = function (req, res) {
    new AllCommand(DAO.getProviderDAO()).execute(req, res);
};
var getCMD = function (req, res) {
    var id = req.params.id;
    new GetCommand(id, DAO.getProviderDAO()).execute(req, res);
};

var createCMD = function (req, res) {
    var provider = Provider.fromJSON(req.body);
    new CreateCommand(provider, DAO.getProviderDAO()).execute(req, res);
};
var updateCMD = function (req, res) {

};
var removeCMD = function (req, res) {
    new RemoveContactCommand(req.params.id, DAO.getContactDAO()).execute(req, res);
};

var testCreateCommand = function (req, res) {
    var provider = new Provider();
    provider.setName('Testing7');
    provider.setSurname('Provider');
    var hrs = new ProviderHours();
    hrs.setDay(1);
    hrs.setStart("08:45");
    hrs.setEnd("09:55");
    provider.setHours(hrs);
    new CreateCommand(provider, DAO.getProviderDAO()).execute(req, res);
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

server.put('/api/providers/:id',
    removeCMD
);
server.get('/api/test/providers/create',
    testCreateCommand
);



