var passport = require('passport');
var GetCurrentCommand = require('../commands/users/GetCurrentUserCommand.js');
var GetTokenCommand = require('../commands/users/GetCurrentAccessToken.js');

var getCurrentUserCMD = function (req, res) {
    new GetCurrentCommand().execute(req, res);
};

var getTokenCMD = function (req, res) {
    new GetTokenCommand().execute(req, res);
};

server = module.exports.server = module.parent.exports.server;

server.get('/api/token',
    server.requiresLogin,
    getTokenCMD
);
server.get('/api/user',
    passport.authenticate('bearer', { session: false }),
    server.requiresLogin,
    getCurrentUserCMD
);
