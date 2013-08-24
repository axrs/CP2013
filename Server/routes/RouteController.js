/**
 * Routes
 *
 * Manages all possible requested routes
 *
 * User: xander
 * Date: 19/08/2013
 * Time: 10:49 PM
 *
 * Revisions:
 * 20130819 - Inital Release
 */

module.exports = function (app) {

    //Required Modules
    var config = require('../config.js')
        , sqlite3 = require('sqlite3');

    var db = new sqlite3.Database(config.sqlite.dbPath);

    /**
     * Exposes the request and response parameters to the express templating system
     * @param req
     * @param res
     * @param next
     */
    exposeLocals = function (req, res, next) {
        res.locals.res = res;
        res.locals.req = req;
        res.viewPath = config.views.path;
        res.statusCodes = require('./libs/StatusHelpers.js');
        next();
    };


    //Reference Models and controllers
    var coreController = require('./CoreController.js')()
        , contact = require('./models/Contact.js')(db)
        , contactController = require('./Controller.js')(contact,config.views.path + 'contacts','contacts','Contacts')
        , contactMiddleware = require('./middleware/ContactMiddleware.js')
        , staff = require('./models/Staff.js')(db)
        , staffController = require('./Controller.js')(staff,config.views.path + 'staff','staff','Staff')
        , staffMiddleware = require('./middleware/StaffMiddleware.js');

    app.locals.deleteButton = require('./libs/helpers').deleteButton;

    //Core routing
    app.get('/', exposeLocals, coreController.index);

    //Contact routing
    app.get('/contacts', exposeLocals, contactController.index);
    app.get('/contacts/new', exposeLocals, contactController.new);
    app.get('/contacts/:id([0-9]+)', exposeLocals, contactMiddleware.loadContactFromDatabase(contact), contactController.show);
    app.post('/contacts', exposeLocals, contactMiddleware.validateContactForm, contactController.create);
    app.get('/contacts/:id([0-9]+)/edit', exposeLocals, contactMiddleware.loadContactFromDatabase(contact), contactController.edit);
    app.put('/contacts/:id([0-9]+)', exposeLocals, contactMiddleware.validateContactForm, contactController.update);

    //Contact API routing
    app.get('/api/contacts', exposeLocals, contactController.apiIndex);
    app.get('/api/contacts/:id([0-9]+)', exposeLocals, contactMiddleware.loadContactFromDatabase(contact, true), contactController.apiShow);
    app.put('/api/contacts', exposeLocals, contactMiddleware.validateAPIContact(contact), contactController.apiCreate);
    app.put('/api/contacts/:id([0-9]+)', exposeLocals, contactMiddleware.validateExistingAPIContact(contact), contactController.apiUpdate);

    //Staff routing
    app.get('/staff', exposeLocals, staffController.index);
    app.get('/staff/new', exposeLocals, staffController.new);
    app.post('/staff', exposeLocals, contactMiddleware.validateContactForm, staffMiddleware.validateStaffForm, staffController.create);

    //The 404 Route (ALWAYS Keep this as the last route)
    app.use(function (req, res) {
        exposeLocals(req, res, function () {
            res.status(404);
            res.render('404',
                {
                    header: 'Error 404 - Resource Not Found'
                });
        });
    });
}