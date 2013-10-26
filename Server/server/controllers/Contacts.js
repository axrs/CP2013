var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');
var Contact = require('../models/Contact.js');
var AllContactsCommand = require('../commands/contacts/AllContactsCommand.js');
var CreateContactCommand = require('../commands/contacts/CreateContactCommand.js');
var UpdateContactCommand = require('../commands/contacts/UpdateContactCommand.js');
var RemoveContactCommand = require('../commands/contacts/RemoveContactCommand.js');
var passport = require('passport');

var DAO = require('../dao/DAO.js');

var allCMD = function (req, res) {
    new AllContactsCommand(DAO.getContactDAO()).execute(req, res);
};
var createCMD = function (req, res) {
    var contact = Contact.fromJSON(req.body);
    new CreateContactCommand(contact, DAO.getContactDAO()).execute(req, res);
};
var updateCMD = function (req, res) {
    var contact = Contact.fromJSON(req.body);
    new UpdateContactCommand(contact, DAO.getContactDAO()).execute(req, res);
};
var removeCMD = function (req, res) {
    new RemoveContactCommand(req.params.id, DAO.getContactDAO()).execute(req, res);
};

server = module.exports.server = module.parent.exports.server;

/**
 * API Routing
 */
server.get('/api/contacts',
    passport.authenticate('bearer', { session: false }),
    server.requiresAdmin,
    allCMD
);
server.put('/api/contacts',
    passport.authenticate('bearer', { session: false }),
    server.requiresAdmin,
    createCMD
);
server.put('/api/contacts/:id',
    passport.authenticate('bearer', { session: false }),
    server.requiresAdmin,
    updateCMD
);
server.delete('/api/contacts/:id',
    passport.authenticate('bearer', { session: false }),
    server.requiresAdmin,
    removeCMD
);

