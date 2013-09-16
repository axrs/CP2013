var async = require('async'),
    config = require('./config');


module.exports = function (app, passport, auth) {


    Contacts = require('../server/controllers/Contacts');
    app.get('/api/contacts', Contacts.all);
    app.get('/api/contacts/new', Contacts.create);

    //Home route
    app.get('/', function (req, res) {
        res.sendfile(path.join(config.publicFolder, 'index.html'))
    })

};