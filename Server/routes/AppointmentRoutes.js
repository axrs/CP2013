app = module.parent.exports.app;

var middleware = require('./middleware/ModelMiddleware.js')
    , appointmentMiddleware = require('./middleware/AppointmentMiddleware.js')
    , model = require('./models/Appointment.js')(app.db)
    , controller = require('./controllers/Controller.js')(model, app.config.views.path + 'appointments', 'appointments', 'Appointments');

app.get('/appointments', app.exposeLocals, controller.index);
app.get('/appointments/new', app.exposeLocals, controller.new);
app.get('/appointments/:id([0-9]+)', app.exposeLocals, middleware.loadFromDatabase(model), controller.show);
app.post('/appointments', app.exposeLocals, appointmentMiddleware.validateForm, controller.create);
app.get('/appointments/:id([0-9]+)/edit', app.exposeLocals, middleware.loadFromDatabase(model), controller.edit);
app.put('/appointments/:id([0-9]+)', app.exposeLocals, appointmentMiddleware.validateForm, controller.update);

