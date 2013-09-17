var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development',
    config = require('./server/config/config.js'),
    fileSystem = require('fs'),
    async = require('async'),
    sqlite = require('sqlite3'),
    database = new sqlite.Database(config.db);


console.log('Running initSql...');
fileSystem.readFile('./resources/initSQL.sql', 'utf8', function (error, data) {
    if (error) throw error;
    database.exec(data);
});

