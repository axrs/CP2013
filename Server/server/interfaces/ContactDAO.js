var Interface = require('./Interface');

var ContactDAO = new Interface('ContactDAO',
    [
        'create', 'remove', 'retrieveAll', 'retrieveById', 'retrieveRange', 'update'
    ]
);

module.exports = ContactDAO;