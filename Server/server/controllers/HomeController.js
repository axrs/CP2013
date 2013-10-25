var passport = require('passport');
server = module.exports.server = module.parent.exports.server;

server.get('/', server.requiresLogin, function (req, res) {
    res.send('Shear-N-Dipity');
});

server.get('/logout', function (req, res) {
    req.logout();
    res.redirect('/');
});

server.get('/api/isAdmin',
    passport.authenticate('bearer', { session: false }),
    server.requiresAdmin,
    function (req, res) {
        res.json(true);
    });