var Ring = require('ring');
var Contact = require('./Contact.js');
var ProviderHours = require('./ProviderHours.js');
var Utilities = require('../utilities/Utilities.js');


var Provider = Ring.create([Contact], {
    _providerId: 0,
    _biography: '',
    _portrait: '',
    _initiated: '',
    _terminated: '',
    _isActive: 1,
    _color: '#006dcc',
    _hours: new Array(7),
    init: function () {
    },
    getId: function () {
        return this._providerId;
    },

    setId: function (value) {
        if (Utilities.isIntegerAboveZero(value)) {
            this._providerId = value;
        }
    },
    getContactId: function () {
        return this._contactId;
    },
    setContactId: function (value) {
        if (Utilities.isInteger(value) && value > 0) {
            this._contactId = value;
        }
    },
    setBiography: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._biography = value;
        }
    },
    getBiography: function () {
        return this._biography;
    },
    setPortrait: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._portrait = value;
        }
    },
    getPortrait: function () {
        return this._portrait;
    },
    setInitiated: function (value) {
        this._initiated = value;
    },
    getInitiated: function () {
        return this._initiated;
    },
    setTerminated: function (value) {
        this._terminated = value;
    },
    getTerminated: function () {
        return this._terminated;
    },
    getIsActive: function () {
        return this._isActive;
    },
    setColor: function (value) {
        this._color = value;
    },
    getColor: function () {
        return this._color;
    },
    setHours: function (value) {
        if (Ring.instance(value, ProviderHours)) {
            this._hours[value.getDay()] = value;
        } else {
            this._hours = value;
        }
    },
    getHours: function (index) {
        if (index) {
            return this._hours[index];
        } else {
            return this._hours;
        }
    },
    toJSON: function () {
        var contactData = this.$super();
        var hours = [];

        for (var i = 0; i < this._hours.length; i++) {
            if (typeof this._hours[i] !== 'undefined' && this._hours[i] !== null) {
                hours[i] = this._hours[i].toJSON();
            }
        }

        var providerData = {
            "providerId": this._providerId,
            "biography": this._biography,
            "portrait": this._portrait,

            "initiated": this._initiated,
            "terminated": this._terminated,
            "isActive": this._isActive,
            "color": this._color,
            "hours": hours
        };

        return Utilities.mergeObjectProperties([contactData, providerData]);
    }
});

Provider.fromJSON = function (data) {

    var provider = new Provider();
    provider.setContactId(data.contactId);
    provider.setSalutation(data.salutation);
    provider.setName(data.name);
    provider.setMiddleName(data.middleName);
    provider.setSurname(data.surname);

    provider.setCompany(data.company);
    provider.setPhone(data.phone);
    provider.setEmail(data.email);

    provider.setAddress(data.address, data.suburb, data.city, data.country, data.state, data.post);

    provider.setId(data.providerId);
    provider.setBiography(data.biography)
    provider.setPortrait(data.portrait);
    provider.setInitiated(data.initiated);
    provider.setTerminated(data.terminated);
    provider.setColor(data.color);

    for (var i = 0; i < data.hours; i++) {
        var ph = ProviderHours.fromJSON(data.hours[i]);
        provider.setHours(i, ph);
    }

    return provider;
};

module.exports = Provider;