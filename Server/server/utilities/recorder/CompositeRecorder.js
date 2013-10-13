var Ring = require('ring');
var IRecorder = require('./IRecorder.js');

/**
 * Composite {IRecorder} comprised of multiple {IRecorder}s
 * @type {*}
 */
var CompositeRecorder = Ring.create([IRecorder], {
    _recorders: [],
    /**
     * Adds a new recorder
     * @param {IRecorder} recorder
     */
    addRecorder: function (recorder) {
        if (Ring.instance(recorder, IRecorder)) {
            this._recorders.push(recorder);
        }
    },
    /**
     * Removes an existing recorder
     * @param {IRecorder} recorder
     */
    removeRecorder: function (recorder) {
        if (Ring.instance(recorder, IRecorder)) {

            var index = this._recorders.indexOf(recorder);
            if (index) {
                this._recorders.splice(index, 1);
            }
        }
    },
    /**
     *
     * @param {String} message to record
     */
    record: function (message) {
        for (var i = 0; i < this._recorders.length; i++) {
            this._recorders[i].record(message);
        }
    }
});

module.exports = CompositeRecorder;