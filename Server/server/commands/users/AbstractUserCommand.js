var Ring = require('ring');
var IUserDAO = require('../../dao/IUserDAO.js');
var ICommand = require('../ICommand.js');

var AbstractUserCommand = Ring.create([ICommand], {
    _dao: null,
    /**
     *
     * @param {IUserDAO} dao
     */
    init: function (dao) {
        if (!Ring.instance(dao, IUserDAO)) {
            throw new Error('Invalid DAO Passed.');
        }
        this._dao = dao;
    }
});

module.exports = AbstractUserCommand;