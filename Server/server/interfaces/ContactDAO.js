var Interface = require('./Interface');

var ContactDAO = new Interface('dao',
    [
        'create', 'remove', 'retrieveAll', 'retrieve', 'retrieveRange', 'update'
    ]
);

module.exports = ContactDAO;