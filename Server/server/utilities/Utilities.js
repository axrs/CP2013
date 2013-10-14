/**
 * Checks if the passed {value} is of type string and not empty.
 * @param value object to be validated
 * @returns {boolean} True if the {value} is string and not empty
 */
module.exports.isStringAndNotEmpty = function (value) {
    return (typeof value == 'string' && value != '');
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
    return '^(20|21|22|23|[01]\d|\d)((:[0-5]\d){1,2})$'.test(value);
};

/**
 * Merges multiple objects into one.
 * @param {Array} objects JSON formatted objects to be merged
 * @returns {object} Merged object properties
 */

module.exports.mergeObjectProperties = function (objects) {
    var merged = {};
    for (var obj in objects) {
        for (var prop in obj) {
            merged[prop] = obj[prop];
        }
    }
    return merged;
};