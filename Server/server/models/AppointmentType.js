var Ring = require('ring');
var Utilities = require('../utilities/Utilities.js');

var AppointmentType = Ring.create({
    _typeId: 0,
    _description: '',
    _duration: '00:00',
    _isAllDay: 0,

    init: function () {
    },

    setId: function (value) {
        if (Utilities.isIntegerAboveZero(value)) {
            this._typeId = 0;
        }
    },
    getId: function () {
        return this._typeId;
    },

    setDuration: function (value) {
        if (Utilities.isTimeStamp(value)) {
            this._duration = value;
        }
    },
    getDuration: function () {
        return this._duration;
    },

    setIsAllDay: function (value) {
        this._isAllDay = value ? 1 : 0;
    },

    getIsAllDay: function () {
        return this._isAllDay;
    },
    setDescription: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._description = value;
        }
    },

    getDescription: function () {
        return this._description;
    },

    toJSON: function () {
        return {
            "typeId": this._typeId,
            "description": this._description,
            "duration": this._duration,
            "isAllDay": this._isAllDay
        }
    }
});

AppointmentType.fromJSON = function (data) {
    var type = new AppointmentType();

    type.setId(data.typeId);
    type.setDuration(data.duration);
    type.setDescription(data.description);
    type.setIsAllDay(data.isAllDay);
    return type;
};

module.exports = AppointmentType;