/**
 * Route Middleware
 *
 * The operation is an abstraction of the routes loading operation typically executed by the show and edit controller
 * methods (refer to the specific routes).
 *
 * This abstraction allows for all routes to use this 'middleware' abstraction to simplify the controller methods.
 *
 * User: xander
 * Date: 8/13/13
 * Time: 6:47 PM
 *
 * Revisions:
 *
 */

module.exports = function (Model) {
    return{
        loadModel: function (message) {
            return function (req, res, next) {
                Model.findById(req.params.id, function (err, record) {
                    if (err) {
                        console.error('Middleware Database Error:\n' + err);
                        res.send(404, 'The ' + message + ' could not be found.');
                    } else if (!record) {
                        console.error('Middleware Database error: ' + record);
                        res.send(500, 'Database error.');
                    } else {
                        req.model = record;
                        next();
                    }
                })
            }
        }
    }
}