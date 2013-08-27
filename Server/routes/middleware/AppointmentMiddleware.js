module.exports = {
    validateForm: function (req, res, next) {
        req.assert('model.appDate', 'No appointment date specified.').notEmpty();
        req.assert('model.servId', 'No service provider specified.').notEmpty();
        req.assert('model.contId', 'No contact reference specified.').notEmpty();
        req.assert('model.appTypeId', 'No appointment type specified.').notEmpty();
        req.assert('model.appTime', 'No appointment time specified.').notEmpty();
        next();
    }
}