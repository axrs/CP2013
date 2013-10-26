var Ring = require('ring');
var ICommand = require('../ICommand.js');

var GetCurrentUserCommand = Ring.create([ICommand], {
    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.write(JSON.stringify(req.user));
        res.end();
    }
});

module.exports = GetCurrentUserCommand;