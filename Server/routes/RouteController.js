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
    locals = function (req, res, next) {
        res.locals.res = res;
        res.locals.req = req;
        res.viewPath = config.views.path;
        next();
    };


    //Reference Models and controllers
    var coreController = require('./CoreController.js')()
        , contact = require('./models/Contact.js')(db)
        , contactController = require('./ContactController.js')(contact)
        , contactMiddleware = require('./middleware/ContactMiddleware.js');

    app.locals.deleteButton = require('./libs/helpers').deleteButton;


    //Core routing
    app.get('/', locals, coreController.index);

    //Contact routing
    app.get('/contacts', locals, contactController.index);
    app.get('/contacts/:id([0-9]+)', locals, contactMiddleware.attemptContactLoad(contact), contactController.show);
    app.get('/contacts/new', locals, contactController.new);
    app.post('/contacts', locals, contactMiddleware.validateContact(), contactController.create);
    app.get('/contacts/:id([0-9]+)/edit', locals, contactMiddleware.attemptContactLoad(contact), contactController.edit);
    app.put('/contacts/:id([0-9]+)',locals, contactMiddleware.validateContact(), contactController.update);

    //The 404 Route (ALWAYS Keep this as the last route)
    app.use(function (req, res) {
        locals(req, res, function () {
            res.status(404);
            res.render('404',
                {
                    header: 'Error 404 - Resource Not Found'
                });
        });
    });
}