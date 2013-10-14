var Ring = require('ring');

var AppointmentType = Ring.create({
    _typeId: 0,
    _description: '',
    _duration: '00:00',
    _isAllDay: 0,

    init: function () {
    },

    setDescription: function (value) {

    },



    toJSON: function () {
        return {

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

module.exports = AppointmentType;