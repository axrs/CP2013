var os = require('os');

module.exports.getLocalAddress = function () {
    var interfaces = os.networkInterfaces();
    var addresses = [];
    for (k in interfaces) {
        for (k2 in interfaces[k]) {
            var address = interfaces[k][k2];
            if (address.family == 'IPv4' && !address.internal) {
                addresses.push(address.address)
            }
        }
    }
    return addresses[0];
};

/**
 * Checks if the passed {value} is of type string and not empty.
 * @param value object to be validated
 * @returns {boolean} True if the {value} is string and not empty
 */
module.exports.isStringAndNotEmpty = function (value) {
    return (typeof value == 'string' && value != '');
};

module.exports.isStringAndEmpty = function (value) {
    return (typeof value == 'string' && value == '');
};

/**
 * Checks if the passed value {n} is an integer
 * @param n object to be checked
 * @returns {boolean} True if {n} is  an integer
 */
module.exports.isInteger = function (n) {
    return typeof n === 'number' && parseFloat(n) == parseInt(n, 10) && !isNaN(n);
};

/**
 * Checks if the passed value {n} is an integer larger than 0
 * @param n object to be checked
 * @returns {boolean} True if {n} is  an integer larger than 0
 */
module.exports.isIntegerAboveZero = function (n) {
    return typeof n === 'number' && parseFloat(n) == parseInt(n, 10) && !isNaN(n) && n > 0;
};

/**
 * Checks if the passed in {value} is a timestamp in the format of 'hh:mm'
 * @param value object to be validated
 * @returns {boolean} True if the {value} matches the format
 */
module.exports.isTimeStamp = function (value) {
    var isValid = RegExp("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$").test(value);
    if (!isValid) {
        isValid = RegExp("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$").test(value);
    }
    return isValid;
};

/**
 * Merges multiple objects into one.
 * @param {Array} objects JSON formatted objects to be merged
 * @returns {object} Merged object properties
 */

module.exports.mergeObjectProperties = function (objects) {
    var merged = {};
    for (var i = 0; i < objects.length; i++) {
        for (var prop in objects[i]) {
            merged[prop] = objects[i][prop];
        }
    }
    return merged;
};