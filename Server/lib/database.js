/**
 * Singleton Database Encapsulation Library
 *
 * User: xander
 * Date: 8/12/13
 * Time: 8:08 PM
 *
 * Revisions:
 * 20130812 - Initial compilation with database generation SQL.
 */

"use strict";

/**
 * SQLite database file path
 * @type {string}
 * @private
 */
var _databasePath = './database/persistent.db';

/**
 * SQLite3 Node.js Module reference
 * @type {*}
 * @private
 */
var _sqlite3 = require('sqlite3').verbose();

/**
 * FileSystem Node.js Module reference
 * @type {*}
 * @private
 */
var _fs = require('fs');

/**
 * SQLite3 Database Reference
 * @type {_sqlite3.Database}
 */
var _database;


/**
 * Obtains the database reference.
 *
 * This method ensures that only one instance of the database is ever open at any one time.
 *
 * @param path {string} OPTIONAL - Database file path from the root installation.
 * @returns {_sqlite3.Database}
 */
exports.database = function (path) {
    if (typeof path !== 'undefined') {
        _databasePath = path;
    }
    if (_database == null) {
        _database = openDatabase();
    }
    return _database;
}

/**
 * Gets the current database file path
 * @returns {string} file path
 */
exports.file = function () {
    return _databasePath;
}

/**
 * Opens the Database.
 * Note: Runs the initialiseDatabase script if no database exists.
 */
function openDatabase() {
    console.log('Opening Database.');
    _fs.exists(_databasePath, function (exists) {
        _database = new _sqlite3.Database(_databasePath);

        if (!exists) {
            console.info('Creating Database.');
            _fs.readFile('./setup/initialiseDatabase.sql', 'utf8', initialiseDatabase);
        }
    });
}

/**
 * Initialises the database in accordance with the SQL script.
 * @param error
 * @param data SQL Data Script
 */
function initialiseDatabase(error, data) {
    if (error) throw error;
    _database.exec(data, function (error) {
        if (error) throw error;
        console.info('Database created.');
    });
}

/**
 * Closes the database file.
 */
function closeDatabase() {
    console.log('Closing Database.');

    if (_database.isOpen()) {
        _database.close();
    }
}