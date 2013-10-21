var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var sqlite = require('sqlite3');
var config = require('../config/config.js');
var database = new sqlite.Database(config.db);
var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');

var dao = new DAOFactory(database).getContactDAO();

var AllContactsCommand = require('../commands/contacts/AllContactsCommand.js');
var CreateContactCommand = require('../commands/contacts/CreateContactCommand.js');
var UpdateContactCommand = require('../commands/contacts/UpdateContactCommand.js');
var RemoveContactCommand = require('../commands/contacts/RemoveContactCommand.js');

var allCMD = function (req, res) {
    new AllContactsCommand(dao).execute(req, res);
};
var createCMD = function (req, res) {
    new CreateContactCommand(dao).execute(req, res);
};
var updateCMD = function (req, res) {
    new UpdateContactCommand(dao).execute(req, res);
};
var removeCMD = function (req, res) {
    new RemoveContactCommand(dao).execute(req, res);
};

server = module.exports.server = module.parent.exports.server;

/**
 * API Routing
 */
server.get('/api/contacts', server.logger, allCMD);
server.put('/api/contacts', server.logger, createCMD);
server.put('/api/contacts/:id', server.logger, updateCMD);
server.del('/api/contacts/:id', server.logger, removeCMD);

