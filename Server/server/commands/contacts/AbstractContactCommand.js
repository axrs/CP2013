var Ring = require('ring');
var IContactDAO = require('../../dao/IContactDAO.js');
var ICommand = require('../ICommand.js');

var AbstractContactCommand = Ring.create([ICommand], {
    _dao: null,
    /**
     *
     * @param {IContactDAO} contactDAO
     */
    init: function (contactDAO) {
        if (!Ring.instance(contactDAO, IContactDAO)) {
            throw new Error('Invalid DAO Passed.');
        }
        this._dao = contactDAO;
    }
});

module.exports = AbstractContactCommand;