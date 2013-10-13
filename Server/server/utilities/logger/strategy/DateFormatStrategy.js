var Ring = require('ring');
var IFormatStrategy = require('./IFormatStrategy.js');

var DateFormatStrategy = Ring.create([IFormatStrategy], {
    format: function (value) {
        var stamp = new Date().toISOString().slice(0, 10);
        return stamp + '\t' + value;
    }
});
module.exports = DateFormatStrategy;