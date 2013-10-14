var Ring = require('ring');
var Utilities = require('../utilities/Utilities.js');

var ProviderHours = Ring.create({
    _day: 0,
    _start: '00:00',
    _breakStart: '00:00',
    _breakEnd: '00:00',
    _end: '00:00',

    init: function (day, start, breakStart, breakEnd, end) {
        if (day) this.setDay(day)
        if (start) this.setStart(start)
        if (breakStart)this.setBreakStart(breakStart);
        if (breakEnd)this.setBreakEnd(breakEnd);
        if (end)this.setEnd(end);
    },

    setDay: function (value) {
        if (Utilities.isInteger(value) && value >= 0 && value <= 6) {
            this._day = value;
        }
    },
    getDay: function () {
        return this._day;
    },

    setStart: function (value) {
        if (Utilities.isTimeStamp(value)) {
            this._start = value;
        }
    },
    getStart: function () {
        return this._start;
    },

    setBreakStart: function (value) {
        if (Utilities.isTimeStamp(value)) {
            this._breakStart = value;
        }
    },
    getBreakStart: function () {
        return this._breakStart;
    },

    setBreakEnd: function (value) {
        if (Utilities.isTimeStamp(value)) {
            this._breakEnd = value;
        }
    },
    getBreakEnd: function () {
        return this._breakEnd;
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
            "day": this._day,
            "start": this._start,
            "breakStart": this._breakStart,
            "breakEnd": this._breakEnd,
            "end": this._end
        }
    }
});

ProviderHours.fromJSON = function (data) {
    var hours = new ProviderHours();
    hours.setDay(data.day);
    hours.setStart(data.start);
    hours.setBreakStart(data.breakStart);
    hours.setBreakEnd(data.breakEnd);
    hours.setEnd(data.end);
    return hours;
};

module.exports = ProviderHours;