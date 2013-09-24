var Interface = require('../interfaces/Interface'),
    Logger = require('./Logger');

function CompositeLogger(loggers) { // extends Logger
    var _loggers = [];

    /**
     * Add logger to known instances
     * @param logger reference
     */
    this.addLogger = function (logger) {
        Interface.ensureImplements(logger, Logger);
        _loggers.push(logger);
    };

    /**
     * Remove logger if known from instances
     * @param logger reference
     */
    this.removeLogger = function (logger) {
        Interface.ensureImplements(logger, Logger);

        var index = _loggers.indexOf(logger);
        if (index) {
            _loggers.splice(index, 1);
        }
    };

    /**
     * Log message to all loggers
     * @param message UTF-8 message to send to all loggers
     */
    this.log = function (message) {
        for (var i = 0; i < _loggers.length; i++) {
            _loggers[i].log(message);
        }
    };
}

module.exports = CompositeLogger;
