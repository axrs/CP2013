var Ring = require('ring');

/**
 * StrategyLogger Interface
 * @type {class}
 * @interface
 */
var ILogger = Ring.create({
    /**
     * Logs a string message
     * @param {String} message
     */
    log: function (message) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = ILogger;