app = module.parent.exports.app;

var appointments = require('./models/Availabilities.js')(app.db)
    , appointmentMiddleware = require('./middleware/AvailabilityMiddleware.js')
    , staff = require('./models/Staff.js')(app.db);

app.get('/api/staff/:id([0-9]+)/available/:date([0-9]{4}-[0-9]{2}-[0-9]{2})',
    app.exposeLocals,
    appointmentMiddleware.availabilitiesById(appointments),
    function (req, res, next) {
        res.set('Content-Type', 'application/json');
        res.send(200, JSON.stringify(req.model));
        res.end();
    }
);

app.get('/api/staff/:id([0-9]+)/available/:start([0-9]{4}-[0-9]{2}-[0-9]{2})/:end([0-9]{4}-[0-9]{2}-[0-9]{2})',
    app.exposeLocals,
    appointmentMiddleware.availabilitiesRangeById(appointments),
    function (req, res, next) {
        res.set('Content-Type', 'application/json');
        res.send(200, JSON.stringify(req.model));
        res.end();
    }
);

app.get('/api/staff/available/:start([0-9]{4}-[0-9]{2}-[0-9]{2})/:end([0-9]{4}-[0-9]{2}-[0-9]{2})',
    app.exposeLocals,
    appointmentMiddleware.availabilitiesRange(appointments),
    function (req, res, next) {
        res.set('Content-Type', 'application/json');
        res.send(200, JSON.stringify(req.model));
        res.end();
    }
);

app.get('/api/staff/appointments/:start([0-9]{4}-[0-9]{2}-[0-9]{2})/:end([0-9]{4}-[0-9]{2}-[0-9]{2})',
    app.exposeLocals,
    appointmentMiddleware.appointmentsRange(appointments),
    function (req, res, next) {
        res.set('Content-Type', 'application/json');
        res.send(200, JSON.stringify(req.model));
        res.end();
    }
);