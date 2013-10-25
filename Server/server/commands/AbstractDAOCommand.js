var Ring = require('ring');
var ICommand = require('./ICommand.js');

var AbstractDAOCommand = Ring.create([ICommand], {
    _dao: null,

    init: function (dao) {
        this._dao = dao;
    }
});

module.exports = AbstractDAOCommand;