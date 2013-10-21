var passport = require('passport');

server = module.exports.server = module.parent.exports.server;
var github_login = passport.authenticate('github');
var github_callback = passport.authenticate('github');

var handler = function (req, res) {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.write(JSON.stringify(req.user));
    res.end();
}

/**
 * API Routing
 */
server.get('/api/auth/github/login', github_login);
server.get('/api/auth/github/callback', github_callback, handler);

