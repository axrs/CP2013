/**
 * Provider Database Model
 *
 * User: xander
 * Date: 23/08/2013
 * Time: 10:44 PM
 *
 * Revisions:
 * 20130823 - Inital Release
 */


module.exports = function (database) {
    return{
        all: function (callback) {
            database.all('SELECT * FROM service_provider LEFT JOIN contact WHERE service_provider.contId = contact.contId;', callback);
        },
        findById: function (id, callback) {
            database.get('SELECT * FROM service_provider LEFT JOIN contact WHERE service_provider.contId = contact.contId AND service_provider.contId = ?;', id, callback);
        },
        matchName: function (name, surname, callback) {
            database.get('SELECT * FROM service_provider LEFT JOIN contact WHERE service_provider.contId = contact.contId AND contForename = ? AND contSurname = ?;', [name, surname], callback);
        },
        count: function (callback) {
            database.get('SELECT count(*) FROM service_provider', null, callback);
        },
        insert: function (data, callback) {
            var contactModel = require('./Contact.js')(database);
            data.contCompany = 'Shear-N-Dipity';

            database.serialize(function () {
                contactModel.insert(data, function (err) {
                    if (!err) {
                        var contactReference = database.get('SELECT contId FROM contact ORDER BY rowid DESC LIMIT 1;')
                        var statement = database.prepare(
                            'INSERT INTO service_provider ' +
                                '(contId, servBio, servPortrait, servInitiated, servIsActive) ' +
                                ' VALUES (?,?,?,?,?);'
                        );
                        statement.run(
                            [
                                contactReference.contId,
                                data.servBio,
                                data.servPortrait,
                                data.servInitiated,
                                (typeof data.servIsActive === 'undefined') ? 1 : data.servIsActive,
                            ],
                            callback
                        );
                    } else {
                        callback(true, null);
                    }
                });
            });
        }
    }
}
