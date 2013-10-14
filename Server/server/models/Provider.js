var Ring = require('ring');
var Contact = require('./Contact.js');
var ProviderHours = require('./ProviderHours.js');


var Provider = Ring.create([Contact], {
    _providerId: 0,
    _biography: '',
    _portrait: '',
    _initiated: '',
    _terminated: '',
    _isActive: 1,
    _color: '#006dcc',
    _hours: [],
    init: function () {
        for (var i = 0; i <= 6; i++) {
            this._hours.push(new ProviderHours(i));
        }
    },
    getId: function () {
        return this._providerId;
    },

    setId: function (value) {
        if (this.isInteger(value) && value > 0) {
            this._providerId = value;
        }
    },
    getContactId: function () {
        return this.$super().getId();
    },
    setContactId: function (value) {
        this.$super().setId(value);
    },
    setBiography: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._biography = value;
        }
    },
    getBiography: function () {
        return this._biography;
    },
    setPortrait: function (value) {
        if (this.isStringAndNotEmpty(value)) {
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

    toJSON: function () {
        var contactData = this.$super();
        var hours = [];

        for (var i = 0; i < this._hours.length; i++) {
            hours[i] = this._hours[i].toJSON();
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

        //Merge Data Sets
        var data = {};
        for (var property in contactData) {
            data[property] = contactData[property];
        }
        for (var attrname in providerData) {
            data[attrname] = providerData[attrname];
        }

        return data;
    }
});

module.exports = Provider;