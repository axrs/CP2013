module.exports = {

    /**
     * Validates POST form fields for required contact details
     * @returns {*}
     */
    validateStaffForm: function (req, res, next) {
        req.assert('model.servInitiated', 'Staff employment date required.').notEmpty();
        next();
    }
}