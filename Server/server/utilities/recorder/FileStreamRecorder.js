var Ring = require('ring');
var IRecorder = require('./IRecorder.js');
var fs = require('fs');

/**
 * FileStreamRecorder
 * @type {*}
 */
var FileStreamRecorder = Ring.create([IRecorder], {
    _stream: null,
    init: function (destination) {
        this._stream = fs.createWriteStream(destination, { 'flags': 'a', 'encoding': 'utf8', 'mode': 0666 });
    },
    /**
     *
     * @param {String} message to record
     */
    record: function (message) {
        if (this._stream != null) {
            _stream.write(message + '\n');
        }
    }
});

module.exports = FileStreamRecorder;