module.exports = {

    /**
     * Validates POST form fields for required contact details
     * @returns {*}
     */
    validateStaffForm: function (req, res, next) {
        req.assert('model.servInitiated', 'Staff employment date required.').notEmpty();
        next();
    },
    validateAPIStaff: function (Model) {

        return function (req, res, next) {

            var provider = req.body;
            req.body.model = provider;

            if (provider && provider.servInitiated) {
                next();
            }
            else res.statusCodes.apiStatus400(req, res);
        }
    },
    /**
     *
     * @param Model
     * @returns {Function}
     */
    validateExistingAPIStaff: function (Model) {
        return function (req, res, next) {
            var provider = req.body;
            req.body.model = provider;
            if (provider && provider.servId && provider.servInitiated) {
                Model.findById(provider.servId, function (err, record) {
                    if (err) res.statusCodes.apiStatus500(req, res);
                    else if (!record) res.statusCodes.apiStatus404(req, res);
                    else next();
                });
            }
            else res.statusCodes.apiStatus400(req, res);
        }
    }

}