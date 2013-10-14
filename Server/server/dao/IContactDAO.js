var Ring = require('ring');

var IContactDAO = Ring.create({
    create: function (contact, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveAll: function (callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveById: function (id, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveByName: function (name, surname, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveRange: function (name, surname, callback) {
        throw new Error('Method Not Implemented.');
    },
    update: function (contact, callback) {
        throw new Error('Method Not Implemented.');
    },
    remove: function (contact, callback) {
        throw new Error('Method Not Implemented.');
    },
    lastInsertedId: function (callback) {
        throw new Error('Method Not Implemented.');
    },
    lastInserted: function (callback) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = IContactDAO;