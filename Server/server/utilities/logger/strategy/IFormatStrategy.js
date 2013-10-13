var Ring = require('ring');

var IFormatStrategy = Ring.create({
    /**
     * Formats a specified value as per the strategy
     * @param {String} value to be formatted
     * @return {String} Formatted value
     */
    format: function (value) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = IFormatStrategy;