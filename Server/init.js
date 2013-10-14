var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('./server/config/config.js');
var fileSystem = require('fs');
var sqlite = require('sqlite3');
var database = new sqlite.Database(config.db);


console.log('Running initSql...');
fileSystem.readFile('./resources/initSQL.sql', 'utf8', function (error, data) {
    if (error) throw error;
    database.exec(data);
});