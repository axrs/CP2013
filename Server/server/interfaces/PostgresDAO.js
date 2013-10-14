var Interface = require('./Interface');

var PostgresDAO = new Interface('PostgresDAO',
    [
        'query'
    ]
);

module.exports = PostgresDAO;