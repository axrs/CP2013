var Ring = require('ring');
var IProviderDAO = require('../../dao/IProviderDAO.js');
var AbstractDAOCommand = require('../AbstractDAOCommand.js');

var AbstractProviderCommand = Ring.create([AbstractDAOCommand], {

    init: function (dao) {
        if (!Ring.instance(dao, IProviderDAO)) {
            throw new Error('Invalid DAO Passed.');
        }
        this.$super(dao);
    }
});

module.exports = AbstractProviderCommand;