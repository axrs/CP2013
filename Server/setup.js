/**
 * Server Setup
 *
 * Used to initialise the server environment prior to execution
 *
 * Note: Not intended to be run on an existing server installation.
 *
 * User: xander
 * Date: 8/13/13
 * Time: 5:37 PM
 *
 * Revisions:
 * 20130813 - Commit
 */


var config = require('./config.js');
var fileSystem = require('fs');

console.log('Checking Database...' + config.sqlite.dbPath);

//Check if the database already exists
fileSystem.exists(config.sqlite.dbPath, function (exists) {
    if (!exists) {
        console.log('Creating database...Please Wait...');

        var sqlite3 = require('sqlite3');
        var database = new sqlite3.Database(config.sqlite.dbPath);

        fileSystem.readFile('setup/initialiseDatabase.sql', 'utf8', function (error, data) {
            if (error) throw error;
            database.exec(data, function (error) {
                if (error) throw error;
                console.info('Database created.');
                callback();
            });
        });
    } else {
        callback();
    }
});