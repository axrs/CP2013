var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var sqlite = require('sqlite3');
var config = require('../config/config.js');
var database = new sqlite.Database(config.db);
var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');
var Contact = require('../models/Contact.js');
var AllContactsCommand = require('../commands/contacts/AllContactsCommand.js');
var CreateContactCommand = require('../commands/contacts/CreateContactCommand.js');
var UpdateContactCommand = require('../commands/contacts/UpdateContactCommand.js');
var RemoveContactCommand = require('../commands/contacts/RemoveContactCommand.js');
var Authorisation = require('../helpers/Authorisation.js');

var dao = new DAOFactory(database).getContactDAO();

var allCMD = function (req, res) {
    new AllContactsCommand(dao).execute(req, res);
};
var createCMD = function (req, res) {
    var contact = Contact.fromJSON(req.body);
    new CreateContactCommand(contact, dao).execute(req, res);
};
var updateCMD = function (req, res) {
    var contact = Contact.fromJSON(req.body);
    new UpdateContactCommand(contact, dao).execute(req, res);
};
var removeCMD = function (req, res) {
    new RemoveContactCommand(req.params.id, dao).execute(req, res);
};

server = module.exports.server = module.parent.exports.server;

/**
 * API Routing
 */
server.get('/api/contacts', server.logger, Authorisation.requiresLogin, allCMD);
server.put('/api/contacts', server.logger, createCMD);
server.put('/api/contacts/:id', server.logger, updateCMD);
server.delete('/api/contacts/:id', server.logger, removeCMD);

