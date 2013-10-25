var Ring = require('ring');
var IContactDAO = require('../../dao/IContactDAO.js');
var AbstractDAOCommand = require('../AbstractDAOCommand.js');

var AbstractContactCommand = Ring.create([AbstractDAOCommand], {
    /**
     *
     * @param {IContactDAO} contactDAO
     */
    init: function (contactDAO) {
        if (!Ring.instance(contactDAO, IContactDAO)) {
            throw new Error('Invalid DAO Passed.');
        }
        this.$super(contactDAO);
    }
});

module.exports = AbstractContactCommand;