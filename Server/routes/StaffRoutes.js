/**
 * Created by Alex on 26/08/13.
 */
app = module.parent.exports.app;

var modelMiddleware = require('./middleware/ModelMiddleware.js')
    , contact = require('./models/Contact.js')(app.db)
    , contactMiddleware = require('./middleware/ContactMiddleware.js')
    , staff = require('./models/Staff.js')(app.db)
    , staffController = require('./controllers/Controller.js')(staff, app.config.views.path + 'staff', 'staff', 'Staff')
    , staffMiddleware = require('./middleware/StaffMiddleware.js');

//Staff routing
app.get('/staff',
    app.exposeLocals,
    staffController.index
);
app.get('/staff/new',
    app.exposeLocals,
    staffController.new
);
app.get('/staff/:id([0-9]+)',
    app.exposeLocals,
    modelMiddleware.loadFromDatabase(staff),
    staffController.show
);
app.post('/staff',
    app.exposeLocals,
    contactMiddleware.validateContactForm,
    staffMiddleware.validateStaffForm,
    staffController.create
);
app.get('/staff/:id([0-9]+)/edit',
    app.exposeLocals,
    modelMiddleware.loadFromDatabase(staff),
    staffController.edit
);
app.put('/staff/:id([0-9]+)',
    app.exposeLocals,
    staffMiddleware.validateStaffForm,
    staffController.update
);

//Staff API routing
app.get('/api/staff',
    app.exposeLocals,
    staffController.apiIndex
);
app.get('/api/staff/:id([0-9]+)',
    app.exposeLocals,
    modelMiddleware.loadFromDatabase(staff, true),
    staffController.apiShow
);
app.put('/staff',
    app.exposeLocals,
    contactMiddleware.validateAPIContact(contact),
    staffMiddleware.validateAPIStaff(staff),
    staffController.apiCreate
);
app.put('/api/staff/:id([0-9]+)',
    app.exposeLocals,
    contactMiddleware.validateExistingAPIContact(contact),
    staffMiddleware.validateExistingAPIStaff(staff),
    staffController.apiUpdate
);