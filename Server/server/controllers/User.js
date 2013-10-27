var passport = require('passport');
var GetCurrentCommand = require('../commands/users/GetCurrentUserCommand.js');
var GetTokenCommand = require('../commands/users/GetCurrentAccessToken.js');
var UpdateUserCommand = require('../commands/users/UpdateUserCommand.js');
var CreateUserCommand = require('../commands/users/CreateUserCommand.js');
var LoginUserCommand = require('../commands/users/LoginUserCommand.js');

var User = require('../models/User.js');

var DAO = require('../dao/DAO.js');

var getCurrentUserCMD = function (req, res) {
    new GetCurrentCommand().execute(req, res);
};

var loginCMD = function (req, res) {
    var user = User.fromJSON(req.body);
    var password = req.body.password;
    new LoginUserCommand(user, password, DAO.getUserDAO()).execute(req, res);
};

var createCMD = function (req, res) {
    var user = User.fromJSON(req.body);
    new CreateUserCommand(user, DAO.getUserDAO()).execute(req, res);
};
var makeUserAdminCMD = function (req, res) {
    var user = req.user;
    user.setIsAdmin(1);
    new UpdateUserCommand(user, DAO.getUserDAO()).execute(req, res);
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

server.put('/api/user/login',
    loginCMD
);

server.put('/api/users/',
    createCMD
);

server.get('/api/test/user/grant',
    passport.authenticate('bearer', { session: false }),
    server.requiresLogin,
    makeUserAdminCMD
);
