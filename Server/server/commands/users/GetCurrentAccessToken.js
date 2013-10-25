var Ring = require('ring');
var ICommand = require('../ICommand.js');

var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('../../config/config.js');


var GetCurrentAccessToken = Ring.create([ICommand], {
    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        res.writeHead(200, { 'Content-Type': 'text/html' });
        res.write('<html><head><title>' + req.user.getToken() + '</title></head>' +
            '<body><script>' +
            'window.onload=function(){window.opener.postMessage("' + req.user.getToken() + '", "' + config.webLocation + '");};' +
            '</script></body>' +
            '</html>');
        res.end();
    }
});

module.exports = GetCurrentAccessToken;