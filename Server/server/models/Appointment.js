var Ring = require('ring');
var Utilities = require('../utilities/Utilities.js');

var Appointment = Ring.create({
    _appointmentId: 0,
    _typeId: 0,
    _contactId: 0,
    _providerId: 0,
    _date: "",
    _time: "",
    _endTime: "",
    _description: "",
    _color: "",

    init: function () {
    },

    setId: function (value) {
        if (Utilities.isIntegerAboveZero(value)) {
            this._appointmentId = value;
        }
    },
    getId: function () {
        return this._appointmentId;
    },

    isValid: function () {
        return (this._typeId != 0 && this._providerId != 0 && this._contactId != 0
            && Utilities.isStringAndNotEmpty(this._date) && Utilities.isStringAndNotEmpty(this._time));
    },
    setTypeId: function (value) {
        if (Utilities.isIntegerAboveZero(value)) {
            this._typeId = value;
        }
    },
    getTypeId: function () {
        return this._typeId;

    },

    setContactId: function (value) {
        if (Utilities.isIntegerAboveZero(value)) {
            this._contactId = value;
        }
    },
    getContactId: function () {
        return this._contactId;
    },
    setProviderId: function (value) {
        if (Utilities.isIntegerAboveZero(value)) {
            this._providerId = value;
        }
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
    setTime: function (value) {
        if (Utilities.isTimeStamp(value)) {
            this._time = value;
        }
    },

    setEndTime: function (value) {
        if (Utilities.isTimeStamp(value)) {
            this._endTime = value;
        }
    },
    setColor: function (value) {
        this._color = value;
    },
    setDescription: function (value) {
        this._description = value;
    },
    getTime: function () {
        return this._time;
    },


    toJSON: function () {
        return {
            "appointmentId": this._appointmentId,
            "typeId": this._typeId,
            "providerId": this._providerId,
            "contactId": this._contactId,
            "description": this._description,
            "date": this._date,
            "time": this._time,
            "endTime": this._endTime,
            "color": this._color

        }
    }
});

Appointment.fromJSON = function (data) {
    var appointment = new Appointment();

    appointment.setId(data.appointmentId);
    appointment.setTypeId(data.typeId);
    appointment.setContactId(data.contactId);
    appointment.setProviderId(data.providerId);
    appointment.setDate(data.date);
    appointment.setTime(data.time);

    return appointment;
};

module.exports = Appointment;