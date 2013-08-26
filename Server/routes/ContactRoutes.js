app = module.parent.exports.app;

var modelMiddleware = require('./middleware/ModelMiddleware.js')
    , contact = require('./models/Contact.js')(app.db)
    , contactController = require('./controllers/Controller.js')(contact, app.config.views.path + 'contacts', 'contacts', 'Contacts')
    , contactMiddleware = require('./middleware/ContactMiddleware.js');


//Contact routing
app.get('/contacts', app.exposeLocals, contactController.index);
app.get('/contacts/new', app.exposeLocals, contactController.new);
app.get('/contacts/:id([0-9]+)', app.exposeLocals, modelMiddleware.loadFromDatabase(contact), contactController.show);
app.post('/contacts', app.exposeLocals, contactMiddleware.validateContactForm, contactController.create);
app.get('/contacts/:id([0-9]+)/edit', app.exposeLocals, modelMiddleware.loadFromDatabase(contact), contactController.edit);
app.put('/contacts/:id([0-9]+)', app.exposeLocals, contactMiddleware.validateContactForm, contactController.update);

//Contact API routing
app.get('/api/contacts', app.exposeLocals, contactController.apiIndex);
app.get('/api/contacts/:id([0-9]+)', app.exposeLocals, modelMiddleware.loadFromDatabase(contact, true), contactController.apiShow);
app.put('/api/contacts', app.exposeLocals, contactMiddleware.validateAPIContact(contact), contactController.apiCreate);
app.put('/api/contacts/:id([0-9]+)', app.exposeLocals, contactMiddleware.validateExistingAPIContact(contact), contactController.apiUpdate);
