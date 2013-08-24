module.exports = {

    /**
     * Attempts to load a contact from the database prior to displaying or interaction.  This allows an abstraction to
     * the controller allowing us intercept any common errors.
     *
     * @param message 'Typical error messages to display'
     * @returns {Function}
     */
    loadStaffFromDatabase: function (Model, isAPI) {

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
    validateStaffForm: function (req, res, next) {
        req.assert('model.servInitiated', 'Staff employment date required.').notEmpty();
        next();
    }
}