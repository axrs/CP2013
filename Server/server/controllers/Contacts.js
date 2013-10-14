var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var sqlite = require('sqlite3');
var config = require('../config/config.js');
var database = new sqlite.Database(config.db);
var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');
var StatusCodes = require('../helpers/StatusHelpers.js');
var Contact = require('../models/Contact.js');


var dao = new DAOFactory(database).getContactDAO();

function all(req, res) {
    dao.retrieveAll(function (err, results) {
        if (err) {
            StatusCodes.status500(req, res);
        } else {
            res.writeHead(200, { 'Content-Type': 'application/json' });
            res.write(JSON.stringify(results));
            res.end();
        }
    });
}
function create(req, res) {
    var contact = Contact.fromJSON(req.body);

    if (!contact.isValid() || contact.getId() > 0) {
        StatusCodes.status400(req, res);
    } else {
        dao.retrieveByName(contact.getName(), contact.getSurname(), function (err, result) {
            if (err) {
                StatusCodes.status500(req, res);
            } else if (result) {
                StatusCodes.status409(req, res);
            } else {
                dao.create(contact, function (err, result) {
                    if (err) {
                        StatusCodes.status500(req, res);
                    } else {
                        dao.lastInserted(function (err, result) {
                            res.writeHead(201, { 'Content-Type': 'application/json' });
                            contact = result;
                            res.write(JSON.stringify(contact));
                            res.end();
                        });
                    }
                });
            }
        });
    }
}
function update(req, res) {
    var contact = new Contact();

    dao.retrieveById(req.params.id, function (err, result) {
        if (err) {
            StatusCodes.status500(req, res);
        } else {
            contact = Contact.fromJSON(req.body);

            if (!contact.isValid() || contact.getId() <= 0) {
                StatusCodes.status400(req, res);
            } else {
                dao.update(contact, function (err, result) {
                    if (err) {
                        StatusCodes.status500(req, res);
                    } else {
                        res.writeHead(202, { 'Content-Type': 'application/json' });
                        res.write(JSON.stringify(contact));
                        res.end();
                    }
                });
            }
        }
    });
    contact = Contact.fromJSON(req.body);
}
function remove(req, res) {
    if (req.params.id > 0) {
        dao.remove(req.params.id, function (err, result) {
            if (err) StatusCodes.status500(req, res);
            else {
                StatusCodes.status202(req, res);
            }
        });
    } else {
        StatusCodes.status400(req, res);
    }
}

app = module.exports.app = module.parent.exports.app;

app.get('/api/contacts', app.logger, all);
app.put('/api/contacts', app.logger, create);
app.post('/api/contacts', app.logger, create);
app.put('/api/contacts/:id', app.logger, update);
app.post('/api/contacts/:id', app.logger, update);
app.delete('/api/contacts/:id', app.logger, remove);