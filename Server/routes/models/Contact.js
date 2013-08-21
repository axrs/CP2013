/**
 * Contact Database Model
 *
 * The contact database model is used to interface with the database to handle all CRUD operations.
 *
 * Note: For the SQLite3 database API
 *       .all() - fetches all records matching the query.
 *       .get() - used to fetch individual records.
 *       .run() - used to update/insert a query without expecting a return result.
 *       .exec() - used in conjunction with .prepare()
 *
 * User: xander
 * Date: 8/13/13
 * Time: 6:21 PM
 *
 * Revisions:
 *
 */


module.exports = function (database) {
    return{
        all: function (callback) {
            database.all('SELECT * FROM contact ORDER BY contForename ASC, contSurname ASC;', callback);
        },
        findById: function (id, callback) {
            database.get('SELECT * FROM contact WHERE contId = ?;', id, callback);
        },
        matchName: function (name, surname, callback) {
            database.get('SELECT * FROM contact WHERE contForename = ? AND contSurname = ?;', [name, surname], callback);
        },
        count: function (callback) {
            database.get('SELECT count(*) FROM contact', null, callback);
        },
        insert: function (data, callback) {
            var statement = database.prepare(
                'INSERT INTO contact ' +
                    '(contForename, contSurname, contCompany, contPhone, contEmail, ' +
                    'contAddrStreet, contAddrSuburb, contAddrCity, contAddrZip, contAddrState)' +
                    ' VALUES (?,?,?,?,?,' +
                    '?,?,?,?,?);'
            );
            statement.run(
                [
                    data.contForename,
                    data.contSurname,
                    data.contCompany,
                    data.contPhone,
                    data.contEmail,
                    (typeof data.contAddrStreet == 'undefined') ? '' : data.contAddrStreet,
                    (typeof data.contAddrSuburb == 'undefined') ? '' : data.contAddrSurburb,
                    (typeof data.contAddrCity == 'undefined') ? '' : data.contAddrCity,
                    (typeof data.contAddrZip == 'undefined') ? '' : data.contAddrZip,
                    (typeof data.contAddrState == 'undefined') ? '' : data.contAddrState
                ],
                callback
            );
        },
        update: function (id, data, callback) {
            var statement = database.prepare(
                'UPDATE contact SET contForename = ?, contSurname = ?, contCompany = ?, contPhone = ?, contEmail = ?, ' +
                    'contAddrStreet = ?, contAddrSuburb = ?, contAddrCity = ?, contAddrZip = ?, contAddrState = ? ' +
                    'WHERE contId = ?;'
            );
            statement.run(
                [
                    data.contForename,
                    data.contSurname,
                    data.contCompany,
                    data.contPhone,
                    data.contEmail,
                    (typeof data.contAddrStreet == 'undefined') ? '' : data.contAddrStreet,
                    (typeof data.contAddrSuburb == 'undefined') ? '' : data.contAddrSuburb,
                    (typeof data.contAddrCity == 'undefined') ? '' : data.contAddrCity,
                    (typeof data.contAddrZip == 'undefined') ? '' : data.contAddrZip,
                    (typeof data.contAddrState == 'undefined') ? '' : data.contAddrState,
                    id
                ],
                callback
            );
        }
    }
}
