var express = require('express'),
    fs = require('fs'),
    passport = require('passport');

var projectDir = __dirname + '/server/';
module.exports = GLOBAL.projectRequire = function (module) {
    return require(projectDir + module);
}

var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development',
    config = require('./server/config/config');
//auth = require('./config/middlewares/authorization'),


var app = express();
require('./server/config/express')(app, null);
require('./server/config/routes')(app, null, null);

app.listen(config.port);
console.log('Express app started on port ' + config.port);

function logger(req, res, next) {
    console.log(req.connection.remoteAddress);
    console.log(req);
    req.on('data', function (d) {
        console.log(d);
    });
    next();
}

app.logger = logger;

module.exports.app = app;

var controllers = __dirname + '/server/controllers';
fs.readdirSync(controllers).forEach(function (file) {
    require(controllers + '/' + file);
});
//require('./config/passport')(passport);

//Home route
app.get('/', function (req, res) {
    res.sendfile(config.publicFolder + '/index.html');
});



