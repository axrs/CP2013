var Authorisation = require('../helpers/Authorisation.js');
var passport = require('passport');
server = module.exports.server = module.parent.exports.server;
server.get('/', Authorisation.requiresLogin, function (req, res) {
    res.send('Shear-N-Dipity');
});
server.get('/logout', function (req, res) {
    req.logout();
    res.redirect('/');
});


server.get('/testing',
    passport.authenticate('bearer', { session: false }),
    function (req, res) {
        res.json({ username: req.user.username, email: req.user.email });
    });