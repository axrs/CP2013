var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development',
    config = projectRequire('config/config'),
    sqlite = require('sqlite3'),
    database = new sqlite.Database(config.db),
    DAOFactory = projectRequire('dao/sqlite/SqliteDAOFactory'),
    StatusCodes = projectRequire('helpers/StatusHelpers.js');


var ContactDAO = new DAOFactory(database).getContactDAO();

function all(req, res) {
    ContactDAO.retrieveAll(function (err, results) {
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
    var Contact = projectRequire('models/Contact');
    var contact = new Contact();
    contact.fromJson(req.body);

    if (!contact.isValid() || contact.getId() > 0) {
        StatusCodes.status400(req, res);
    } else {
        ContactDAO.retrieveByName(contact.getForename(), contact.getSurname(), function (err, result) {
            if (err) {
                StatusCodes.status500(req, res);
            } else if (result) {
                StatusCodes.status409(req, res);
            } else {
                ContactDAO.create(contact, function (err, result) {
                    console.log(result);
                    if (err) {
                        StatusCodes.status500(req, res);
                    } else {
                        ContactDAO.lastInsertedContact(function (err, result) {
                            res.writeHead(201, { 'Content-Type': 'application/json' });
                            contact.fromJson(result);
                            res.write(JSON.stringify(contact.toJSON()));
                            res.end();
                        });
                    }
                });
            }
        });
    }
}
function update(req, res) {
    var Contact = projectRequire('models/Contact');
    var contact = new Contact();

    ContactDAO.retrieveById(req.params.id, function (err, result) {
        if (err) {
            StatusCodes.status500(req, res);
        } else {
            contact.fromJson(result);
            contact.fromJson(req.body);

            if (!contact.isValid() || contact.getId() <= 0) {
                StatusCodes.status400(req, res);
            } else {
                ContactDAO.update(contact, function (err, result) {
                    if (err) {
                        StatusCodes.status500(req, res);
                    } else {
                        res.writeHead(202, { 'Content-Type': 'application/json' });
                        res.write(JSON.stringify(contact.toJSON()));
                        res.end();
                    }
                });
            }
        }
    });
    contact.fromJson(req.body);
}
function remove(req, res) {
    if (req.params.id > 0) {
        ContactDAO.remove(req.params.id, function (err, result) {
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