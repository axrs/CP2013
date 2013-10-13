var Ring = require('ring');
var IRecorder = require('./IRecorder.js');
var ChildProcess = require('child_process');

/**
 * ActiveFileRecorder
 * @type {*}
 */
var ActiveFileRecorder = Ring.create([IRecorder], {
    _fileRecorder: null,
    init: function (path) {
        this._fileRecorder = ChildProcess.fork(__dirname + '/FileRecorderProcess.js');
        this._fileRecorder.send({filePath: path});
    },
    /**
     *
     * @param {String} message to record
     */
    record: function (message) {
        if (this._fileRecorder != null) {
            this._fileRecorder.send(message);
        }
    }
});

module.exports = ActiveFileRecorder;