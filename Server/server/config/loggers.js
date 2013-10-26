var StrategyLogger = require('../utilities/logger/StrategyLogger.js');
var ConsoleRecorder = require('../utilities/recorder/ConsoleRecorder.js');
var CompositeRecorder = require('../utilities/recorder/CompositeRecorder.js');
var ActiveFileRecorder = require('../utilities/recorder/ActiveFileRecorder.js');
var DateTimeFormatStrategy = require('../utilities/logger/strategy/DateTimeFormatStrategy.js');
var LogEventDispatcher = require('../utilities/LogEventDispatcher.js');

module.exports = function (server) {
    var cr = new CompositeRecorder();
    cr.addRecorder(new ConsoleRecorder());
    cr.addRecorder(new ActiveFileRecorder('./logs/general.log'));
    var l = new StrategyLogger(cr);
    l.setStrategy(new DateTimeFormatStrategy());

    LogEventDispatcher.getInstance().on('log', function (message) {
        l.log(message);
    });

    var recorders = new CompositeRecorder();
    recorders.addRecorder(new ConsoleRecorder());
    recorders.addRecorder(new ActiveFileRecorder('./logs/request.log'));
    var loggers = new StrategyLogger(recorders);
    loggers.setStrategy(new DateTimeFormatStrategy());

    function logRequest(req, res, next) {
        var message = req.sessionID + "\t" + req.connection.remoteAddress + "\t" + req.url;
        loggers.log(message + "\t" + JSON.stringify(req.body));
        next();
    }

    server.use(logRequest);
};