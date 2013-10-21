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

app = module.exports.app = module.parent.exports.app;

/**
 * API Routing
 */
app.get('/api/contacts', app.logger, allCMD);
app.put('/api/contacts', app.logger, createCMD);
app.post('/api/contacts', app.logger, createCMD);
app.put('/api/contacts/:id', app.logger, updateCMD);
app.post('/api/contacts/:id', app.logger, updateCMD);
app.delete('/api/contacts/:id', app.logger, removeCMD);

