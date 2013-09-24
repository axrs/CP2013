var fs = require('fs');

function FileStreamLogger(destination) { // extends Logger

    var _stream = fs.createWriteStream(destination, { 'flags': 'a', 'encoding': 'utf8', 'mode': 0666 });

    this.log = function (message) {
        console.log('here');
        _stream.write(message + '\n');
    }
}

module.exports = FileStreamLogger;
