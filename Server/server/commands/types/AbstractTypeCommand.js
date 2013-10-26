var Ring = require('ring');
var ITypeDAO = require('../../dao/ITypeDAO.js');
var AbstractDAOCommand = require('../AbstractDAOCommand.js');

var AbstractTypeCommand = Ring.create([AbstractDAOCommand], {
    /**
     *
     * @param {ITypeDAO} dao
     */
    init: function (dao) {
        if (!Ring.instance(dao, ITypeDAO)) {
            throw new Error('Invalid DAO Passed.');
        }
        this.$super(dao);
    }
});

module.exports = AbstractTypeCommand;