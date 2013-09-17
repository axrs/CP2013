var Interface = require('./Interface');

var DAOFactory = new Interface('DAOFactory',
    [
        'getContactDAO', 'getUserDAO'
    ]
);

module.exports = DAOFactory;