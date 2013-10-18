var Ring = require('ring');
var IContactDAO = require('./IContactDAO.js');

var IUserDAO = Ring.create([IContactDAO], {
    retrieveByUserName: function (username, callback) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = IUserDAO;
