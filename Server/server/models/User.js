var Ring = require('ring');
var Contact = require('./Contact.js');
var Crypto = require('crypto');
var Utilities = require('../utilities/Utilities.js');

var User = Ring.create([Contact], {
    _saltLength: 24,

    _userId: 0,
    getId: function () {
        return this._userId;
    },
    setId: function (value) {
        if (Utilities.isInteger(value) && value > 0) {
            this._userId = value;
        }
    },

    isValid: function () {
        if (this.getStrategy() === 'local') {
            return (this.$super() && Utilities.isStringAndNotEmpty(this.getPassword()));
        } else {
            return true;
        }
    },

    _token: null,
    getToken: function () {
        return this._token;
    },
    setToken: function (value) {
        if (value != null) {
            this._token = value;
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

    _strategy: 'local',
    getStrategy: function () {
        return this._strategy;
    },
    setStrategy: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._strategy = value;
        }
    },

    _strategyData: '',
    getStrategyData: function () {
        return this._strategyData;
    },
    setStrategyData: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._strategyData = value;
        }
    },

    _strategyId: 0,
    getStrategyId: function () {
        return this._strategyId;
    },
    setStrategyId: function (value) {
        if (Utilities.isInteger(value) && value > 0) {
            this._strategyId = value;
        }
    },

    _user: '',
    setUserName: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._user = value;
        }
    },
    getUserName: function () {
        return this._user;
    },

    _password: '',
    validatePassword: function (test) {
        return this.validateHash(this._password, test);
    },
    setHashedPassword: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._password = value;
        }
    },
    setPassword: function (value) {
        if (Utilities.isStringAndNotEmpty(value)) {
            this._password = this.createHash(value);
        }
    },
    getPassword: function () {
        return this._password;
    },

    _isAdmin: 0,
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
        var contactData = this.$super();

        var userData = {
            "userId": this._userId,
            "strategyId": this._strategyId,
            "strategy": this._strategy,
            "strategyData": this._strategyData,
            "userName": this._user,
            "isAdmin": this._isAdmin,
            "token": this._token,
            "md5Email": Crypto.createHash('md5').update(this._email).digest("hex")
        };

        return Utilities.mergeObjectProperties([contactData, userData]);
    }
});


User.fromJSON = function (data) {

    var user = new User();
    user.setContactId(data.contactId);
    user.setSalutation(data.salutation);
    user.setName(data.name);
    user.setMiddleName(data.middleName);
    user.setSurname(data.surname);

    user.setCompany(data.company);
    user.setPhone(data.phone);
    user.setEmail(data.email);

    user.setAddress(data.address, data.suburb, data.city, data.country, data.state, data.post);

    user.setId(data.userId);
    user.setUserName(data.userName);

    if (data.userId) {
        user.setHashedPassword(data.password);
    } else {
        user.setPassword(data.password);
    }
    user.setStrategyId(data.strategyId);

    user.setStrategy(data.strategy);
    user.setStrategyData(data.strategyData);
    user.setIsAdmin(data.isAdmin);

    return user;
};

module.exports = User;