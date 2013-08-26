module.exports = {

    /**
     * Validates POST form fields for required contact details
     * @returns {*}
     */
    validateAppointmentTypeForm: function (req, res, next) {
        req.assert('model.appTypeDescription', 'An appointment type description is required.').notEmpty();
        req.assert('model.appTypeDuration', 'An appointment duration is required.').notEmpty();
        next();
    },

    validateAPIAppointmentType: function (Model) {
        return function (req, res, next) {

            var appointmentType = req.body;
            req.body.model = appointmentType;

            if (appointmentType && appointmentType.appTypeDescription && appointmentType.appTypeDuration) {
                next();
            }
            else res.statusCodes.apiStatus400(req, res);
        }
    },
    validateExistingAPIAppointmentType: function (Model) {
        return function (req, res, next) {
            var appointmentType = req.body;

            req.body.model = appointmentType;
            if (appointmentType && appointmentType.appTypeId
                && appointmentType.appTypeDescription
                && appointmentType.appTypeDuration) {
                Model.findById(appointmentType.appTypeId, function (err, record) {
                    if (err) res.statusCodes.apiStatus500(req, res);
                    else if (!record) res.statusCodes.apiStatus404(req, res);
                    else next();
                });
            }
            else res.statusCodes.apiStatus400(req, res);
        }
    }
}
