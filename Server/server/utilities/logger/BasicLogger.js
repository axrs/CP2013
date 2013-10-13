var Ring = require('ring');
var ILogger = require('./ILogger.js');

/**
 *
 * @type {*}
 */
var BasicLogger = Ring.create([ILogger], {
    /**
     * {IRecorder} recorder
     */
    _recorder: null,

    /**
     * @constructor
     * @param {IRecorder} recorder
     */
    init: function (recorder) {
        this._recorder = recorder;
    },

    /**
     * Logs a string to the recorder
     * @param {String} message to log
     * @override
     */
    log: function (message) {
        if (this._recorder != null) {
            this._recorder.record(message);
        }
    }
});

module.exports = BasicLogger;