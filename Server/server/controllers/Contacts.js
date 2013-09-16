var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development',
    config = require('../../config/config'),
    sqlite = require('sqlite3'),
    database = new sqlite.Database(config.db),
    DAOFactory = require('../dao/sqlite-dao/SqliteDaoFactory');

var ContactDAO = new DAOFactory(database).getContactDAO();

module.exports = {
    all: function (req, res) {
        ContactDAO.retrieveAll(function (err, results) {
            if (err) console.log(err);
            res.json(results);
        });
    },
    create: function (req, res) {
        var Contact = require('../models/Contact');
        var contact = new Contact();
        contact.setForename('Alexander');
        contact.setSurname('Scott');
        console.log('here');

        ContactDAO.create(contact, function (result) {
            console.log(result);
        });
    }
};
