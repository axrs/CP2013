var Ring = require('ring');
var IFormatStrategy = require('./IFormatStrategy.js');

var TimeFormatStrategy = Ring.create([IFormatStrategy], {
    format: function (value) {
        var stamp = new Date().toLocaleTimeString();
        return stamp + '\t' + value;
    }
});
module.exports = TimeFormatStrategy;