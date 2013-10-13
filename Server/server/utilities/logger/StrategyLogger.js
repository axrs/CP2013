var Ring = require('ring');
var BasicLogger = require('./BasicLogger.js');
var IFormatStrategy = require('./strategy/IFormatStrategy.js');
var TimeFormatStrategy = require('./strategy/TimeFormatStrategy.js');


var StrategyLogger = Ring.create([BasicLogger], {
    _formatStrategy: new TimeFormatStrategy(),
    /**
     * @constructor
     */
    init: function (recorder, strategy) {
        this.$super(recorder);
        this.setStrategy(strategy);
    },
    /**
     * Sets the formatting strategy
     * @param {IFormatStrategy} strategy
     */
    setStrategy: function (strategy) {
        if (Ring.instance(strategy, IFormatStrategy)) {
            this._formatStrategy = strategy;
        }
    },
    /**
     * Logs a message to the recorder
     * @param {String} message
     */
    log: function (message) {
        this.$super(this._formatStrategy.format(message));
    }
});
module.exports = StrategyLogger;