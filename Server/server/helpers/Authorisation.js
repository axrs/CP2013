exports.requiresLogin = function (req, res, next) {
    if (!req.isAuthenticated()) {
        return res.send(401, 'Not Authorised.');
    }
    next();
};

exports.requiresAdmin = function (req, res, next) {
    if (!req.getIsAdmin()) {
        return res.send(401, 'Requires Administration Privileges');
    }
    next();
};