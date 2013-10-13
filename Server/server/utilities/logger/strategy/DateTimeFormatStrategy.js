var Ring = require('ring');
var IFormatStrategy = require('./IFormatStrategy.js');

var DateTimeFormatStrategy = Ring.create([IFormatStrategy], {
    format: function (value) {
        var date = new Date();
        var stamp = date.toISOString().slice(0, 10) + " " + date.toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
        return stamp + '\t' + value;
    }
});
module.exports = DateTimeFormatStrategy;