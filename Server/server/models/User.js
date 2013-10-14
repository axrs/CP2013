var Ring = require('ring');
var Contact = require('./Contact.js');
var Crypto = require('crypto');

var User = Ring.create([Contact], {
    _saltLength: 24,
    _userId: 0,
    _user: '',
    _password: '',
    _isAdmin: 0,
    _strategy: 'local',
    _strategyData: {},

    getId: function () {
        return this._userId;
    },
    setId: function (value) {
        if (this.isInteger(value) && value > 0) {
            this._userId = value;
        }
    },

    validatePassword: function (test) {
        return this.validateHash(this._password, test);
    },
    setPassword: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._password = this.createHash(value);
        }
    },
    setUserName: function (value) {
        if (this.isStringAndNotEmpty(value)) {
            this._user = value;
        }
    },
    getUser: function () {
        return this._user;
    },
    setIsAdmin: function (value) {
        if (value) {
            this._isAdmin = 1;
        }
    },
    getIsAdmin: function () {
        return this._isAdmin == 1;
    },

    createHash: function (password) {
        var salt = this.generateSalt(this._saltLength);
        var hash = this.hash(password + salt);
        return salt + hash;
    },
    validateHash: function (hash, password) {
        var salt = hash.substr(0, this._saltLength);
        var validHash = salt + this.hash(password + salt);
        return hash === validHash;
    },
    generateSalt: function (len) {
        var set = '0123456789abcdefghijklmnopqurstuvwxyzABCDEFGHIJKLMNOPQURSTUVWXYZ',
            setLen = set.length,
            salt = '';
        for (var i = 0; i < len; i++) {
            var p = Math.floor(Math.random() * setLen);
            salt += set[p];
        }
        return salt;
    },
    hash: function (string) {
        return Crypto.createHash('md5').update(string).digest('hex');
    },

    toJSON: function () {

    }
});


User.fromJSON = function (data) {

};

module.exports = User;