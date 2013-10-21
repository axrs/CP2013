var fs = require('fs');
var passport = require('passport');
var express = require('express');
var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('./server/config/config');


var server = express();
require('./server/config/passport')(passport);
require('./server/config/express')(server, passport);

server.listen(config.port);
console.log('Express app started on port ' + config.port);


var StrategyLogger = require('./server/utilities/logger/StrategyLogger.js');
var ConsoleRecorder = require('./server/utilities/recorder/ConsoleRecorder.js');
var CompositeRecorder = require('./server/utilities/recorder/CompositeRecorder.js');
var ActiveFileRecorder = require('./server/utilities/recorder/ActiveFileRecorder.js');
var DateTimeFormatStrategy = require('./server/utilities/logger/strategy/DateTimeFormatStrategy.js');
var LogEventDispatcher = require('./server/utilities/LogEventDispatcher.js');

var cr = new CompositeRecorder();
cr.addRecorder(new ConsoleRecorder());
cr.addRecorder(new ActiveFileRecorder('./logs/general.log'));
var l = new StrategyLogger(cr);
l.setStrategy(new DateTimeFormatStrategy());

LogEventDispatcher.getInstance().on('log', function (message) {
    l.log(message);
});

var recorders = new CompositeRecorder();
recorders.addRecorder(new ActiveFileRecorder('./logs/request.log'));
var loggers = new StrategyLogger(recorders);
loggers.setStrategy(new DateTimeFormatStrategy());

function logRequest(req, res, next) {
    var message = req.connection.remoteAddress + "\t" + req.url;
    loggers.log(message + "\t" + JSON.stringify(req.body));
    next();
}

server.logger = logRequest;

module.exports.server = server;


var controllers = __dirname + '/server/controllers';
fs.readdirSync(controllers).forEach(function (file) {
    require(controllers + '/' + file);
});



