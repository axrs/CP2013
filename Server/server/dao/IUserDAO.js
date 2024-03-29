var Ring = require('ring');
var IContactDAO = require('./IContactDAO.js');

var IUserDAO = Ring.create([IContactDAO], {
    retrieveByUserName: function (username, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveById: function (id, strategy, callback) {
        throw new Error('Method Not Implemented.');
    },
    retrieveByToken: function (token, callback) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = IUserDAO;
