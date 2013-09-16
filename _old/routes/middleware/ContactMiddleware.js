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
     * Validates POST form fields for required contact details
     * @returns {*}
     */
    validateContactForm: function (req, res, next) {
        req.assert('model.contForename', 'No first name specified.').notEmpty();
        req.assert('model.contSurname', 'No last name specified.').notEmpty();
        next();
    },
    /**
     * Validates a PUT from the API for the required contact details
     * @returns {Function}
     */
    validateAPIContact: function (Model) {

        return function (req, res, next) {

            var contact = req.body;
            req.body.model = contact;

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
            var contact = req.body;

            req.body.model = contact;
            if (contact && contact.contId && contact.contForename && contact.contSurname) {
                Model.findById(contact.contId, function (err, record) {
                    if (err) res.statusCodes.apiStatus500(req, res);
                    else if (!record) res.statusCodes.apiStatus404(req, res);
                    else next();
                });
            }
            else res.statusCodes.apiStatus400(req, res);
        }
    }
}
