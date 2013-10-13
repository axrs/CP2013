var Ring = require('ring');

/**
 * Recorder Interface
 * @type {class}
 * @interface
 */
var IRecorder = Ring.create({
    /**
     * Records a string message
     * @param {String} message
     */
    record: function (message) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = IRecorder;

