var Ring = require('ring');
var IRecorder = require('./IRecorder.js');

/**
 * ConsoleRecorder
 * @type {*}
 */
var ConsoleRecorder = Ring.create([IRecorder], {
    /**
     *
     * @param {String} message to record
     */
    record: function (message) {
        console.log(message);
    }
});

module.exports = ConsoleRecorder;