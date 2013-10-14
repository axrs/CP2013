var Ring = require('ring');

var IUserDAO = Ring.create({
    create: function (user, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveAll: function (callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveById: function (id, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveByUsername: function (username, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveRange: function (username, callback) {
        throw new Error('Method Not Implemented.');
    },
    update: function (user, callback) {
        throw new Error('Method Not Implemented.');
    },
    remove: function (user, callback) {
        throw new Error('Method Not Implemented.');
    },
    lastInsertedId: function (callback) {
        throw new Error('Method Not Implemented.');
    },
    lastInsertedUser: function (callback) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = IUserDAO;