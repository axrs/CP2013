var passport = require('passport');

server = module.exports.server = module.parent.exports.server;
var github_login = passport.authenticate('github');
var github_callback = passport.authenticate('github', {successRedirect: '/api/token'});

/**
 * API Routing
 */
server.get('/api/auth/github/login', server.logger, github_login);
server.get('/api/auth/github/callback', server.logger, github_callback);

