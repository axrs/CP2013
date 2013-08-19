/**
 * Contact Middleware
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

module.exports = {

    /**
     * Attempts to load a contact from the database prior to displaying or interaction.  This allows an abstraction to
     * the controller allowing us intercept any common errors.
     *
     * @param message 'Typical error messages to display'
     * @returns {Function}
     */
    attemptContactLoad: function (Model) {
        return function (req, res, next) {
            Model.findById(req.params.id, function (err, record) {
                if (err) {
                    console.error('Middleware Database Error:\n' + err);

                    res.render(res.viewPath + '404',
                        {
                            header: 'Error 404'
                        });
                } else if (!record) {
                    console.error('Middleware Database error: ' + record);
                    res.render(res.viewPath + '500',
                        {
                            header: 'Error 500'
                        });
                } else {
                    req.model = record;
                    next();
                }
            })
        }
    },
    validateContact: function () {
        return function (req, res, next) {
            contact = req.params.contact;

            if (typeof contact.contForname == 'undefined' || contact.contForename == '') {
                console.log('attempted to register contact without forename');
            }
        }
    }
}