var Interface = require('./Interface');

var ContactDAO = new Interface('dao',
    [
        'create', 'remove', 'retrieveAll', 'retrieveById', 'retrieveRange', 'update'
    ]
);

module.exports = ContactDAO;