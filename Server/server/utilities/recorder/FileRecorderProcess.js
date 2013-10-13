var FileStreamRecorder = require('./FileStreamRecorder.js');

var recorder = null;

process.on('message', function (m) {
    if (recorder !== null) {
        recorder.record(m);
    } else if ((typeof m.filePath) !== "undefined") {
        recorder = new FileStreamRecorder(m.filePath);
    }
});

