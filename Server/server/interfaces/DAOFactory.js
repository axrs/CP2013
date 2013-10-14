var Interface = require('./Interface');

var DAOFactory = new Interface('DAOFactory',
    [
        'getContactDAO'
    ]
);

module.exports = DAOFactory;