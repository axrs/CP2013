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




//Expose the core server for all controllers
app = module.exports.app = module.parent.exports.app;

var coreController = require('./CoreRoutes.js')();

app.get('/', app.exposeLocals, coreController.index);

require('./ContactRoutes.js');
require('./StaffRoutes.js');
require('./AppointmentTypeRoutes.js');
require('./AppointmentRoutes.js');

//The 404 Route (ALWAYS Keep this as the last route)
app.use(function (req, res) {
    app.exposeLocals(req, res, function () {
        res.status(404);
        res.render('404',
            {
                header: 'Error 404 - Resource Not Found'
            });
    });
});

