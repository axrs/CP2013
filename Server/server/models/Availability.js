var Ring = require('ring');
var Utilities = require('../utilities/Utilities.js');

var Availability = Ring.create({
    _providerId: 0,
    _date: "",
    _start: "00:00",
    _end: "23:45",
    _color: "",

    init: function () {
    },

    setId: function (value) {
        if (Utilities.isIntegerAboveZero(value)) {
            this._providerId = value;
        }
    },
    getId: function () {
        return this._providerId;
    },
    setProviderId: function (value) {
        if (Utilities.isIntegerAboveZero(value)) {
            this._providerId = value;
        }
    },
    setColor: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._color = value;
        }
    },
    getColor: function () {
        return this._color;
    },
    getProviderId: function () {
        return this._providerId;
    },
    setDate: function (value) {
        this._date = value;
    },
    getDate: function () {
        return this._date;
    },
    setStart: function (value) {
        if (Utilities.isTimeStamp(value)) {
            this._start = value;
        }
    },
    getStart: function () {
        return this._start;
    },

    setEnd: function (value) {
        if (Utilities.isTimeStamp(value)) {
            this._end = value;
        }
    },
    getEnd: function () {
        return this._end;
    },
    toJSON: function () {
        return {
            "providerId": this._providerId,
            "date": this._date,
            "start": this._start,
            "end": this._end,
            "color": this._color
        }
    }
});

module.exports = Availability;