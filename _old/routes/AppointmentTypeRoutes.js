app = module.parent.exports.app;

var modelMiddleware = require('./middleware/ModelMiddleware.js')
    , types = require('./models/AppointmentType.js')(app.db)
    , controller = require('./controllers/Controller.js')(types, app.config.views.path + 'types', 'appointments/types', 'Appointment Type')
    , middleware = require('./middleware/AppointmentTypeMiddleware.js');


//types routing
app.get('/appointments/types', app.exposeLocals, controller.index);
app.get('/appointments/types/new', app.exposeLocals, controller.new);
app.get('/appointments/types/:id([0-9]+)', app.exposeLocals, modelMiddleware.loadFromDatabase(types), controller.show);
app.post('/appointments/types', app.exposeLocals, middleware.validateAppointmentTypeForm, controller.create);
app.get('/appointments/types/:id([0-9]+)/edit', app.exposeLocals, modelMiddleware.loadFromDatabase(types), controller.edit);
app.put('/appointments/types/:id([0-9]+)', app.exposeLocals, middleware.validateAppointmentTypeForm, controller.update);

//types API routing
app.get('/api/appointments/types', app.exposeLocals, controller.apiIndex);
app.get('/api/appointments/types/:id([0-9]+)', app.exposeLocals, modelMiddleware.loadFromDatabase(types, true), controller.apiShow);
app.put('/api/appointments/types', app.exposeLocals, middleware.validateAPIAppointmentType(types), controller.apiCreate);
app.put('/api/appointments/types/:id([0-9]+)', app.exposeLocals, middleware.validateExistingAPIAppointmentType(types), controller.apiUpdate);
