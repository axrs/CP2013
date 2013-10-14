var Ring = require('ring');
var ICRUDDAO = require('./ICRUDDAO.js');

var IContactDAO = Ring.create([ICRUDDAO], {
    retrieveByName: function (name, surname, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveRange: function (name, surname, callback) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = IContactDAO;