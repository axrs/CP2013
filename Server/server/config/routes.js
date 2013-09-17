var async = require('async'),
    config = require('./config');


module.exports = function (app, passport, auth) {
    Contacts = require('./../controllers/Contacts');
    app.get('/api/contacts', Contacts.all);
    app.put('/api/contacts', Contacts.create);
    app.post('/api/contacts', Contacts.create);

    app.delete('/api/contacts/:id', Contacts.remove);
    //Home route
    app.get('/', function (req, res) {
        res.sendfile(config.publicFolder + '/index.html');
    })

};