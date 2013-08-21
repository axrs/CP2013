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
    attemptContactLoad: function (Model, isAPI) {
        return function (req, res, next) {
            Model.findById(req.params.id, function (err, record) {

                if (isAPI) {
                    if (err) {
                        res.end(JSON.stringify(false));
                    } else if (!record) {
                        res.end(JSON.stringify(null));
                    } else {
                        req.model = record;
                        next();
                    }
                } else {
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
                }

            })
        }
    },
    /**
     * Validates POST form fields for required contact details
     * @returns {*}
     */
    validateContact: function () {
        var form = require('express-form')
            , field = form.field
            , filter = form.filter
            , validate = form.validate;
        ;
        return form(
            filter('contact.contForename').trim(),
            validate("contact.contForename", "first name").required("", "No %s specified."),
            filter('contact.contSurname').trim(),
            validate("contact.contSurname", "surname").required("", "No %s specified.")
        );
    }
}