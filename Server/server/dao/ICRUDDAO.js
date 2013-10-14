var Ring = require('ring');

var ICRUDDAO = Ring.create({
    create: function (value, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveAll: function (callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveById: function (id, callback) {
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

module.exports = ICRUDDAO;