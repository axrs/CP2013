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
                    if (err) res.statusCodes.apiStatus500(req, res);
                    else if (!record) res.statusCodes.apiStatus404(req, res);
                    else {
                        req.model = record;
                        next();
                    }
                } else {
                    if (err) {
                        res.statusCodes.status500(req, res, next);
                    } else if (!record) {
                        res.statusCodes.status404(req, res, next);
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
    },
    /**
     * Validates a PUT from the API for the required contact details
     * @returns {Function}
     */
    validateAPIContact: function (Model) {

        return function (req, res, next) {

            var contact = req.body.model;

            if (contact && contact.contForename && contact.contSurname) {
                Model.matchName(contact.contForename, contact.contSurname, function (err, record) {
                    if (err) res.statusCodes.apiStatus500(req, res);
                    else if (record) res.statusCodes.apiStatus409(req, res);
                    else next();
                });
            }
            else res.statusCodes.apiStatus400(req, res);
        }
    },
    /**
     *
     * @param Model
     * @returns {Function}
     */
    validateExistingAPIContact: function (Model) {
        return function (req, res, next) {
            var contact = req.body.model;

            if (contact && contact.contId && contact.contForename && contact.contSurname) {
                Model.findById(req.params.id, function (err, record) {
                    if (err) res.statusCodes.apiStatus500(req, res);
                    else if (!record) res.statusCodes.apiStatus404(req, res);
                    else next();
                });
            }
            else res.statusCodes.apiStatus400(req, res);
        }
    }
}