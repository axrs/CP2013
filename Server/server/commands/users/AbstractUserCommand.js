var Ring = require('ring');
var IUserDAO = require('../../dao/IUserDAO.js');
var AbstractDAOCommand = require('../AbstractDAOCommand.js');

var AbstractUserCommand = Ring.create([AbstractDAOCommand], {
    /**
     *
     * @param {IUserDAO} dao
     */
    init: function (dao) {
        if (!Ring.instance(dao, IUserDAO)) {
            throw new Error('Invalid DAO Passed.');
        }
        this.$super(dao);
    }
});

module.exports = AbstractUserCommand;