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

            var allStaff = [];
            var total = 0;

            function getAllStaff() {
                database.serialize(function () {

                    database.each('SELECT * FROM service_provider LEFT JOIN contact WHERE service_provider.contId = contact.contId;', function (err, result) {
                            database.serialize(function () {
                                database.all('SELECT servHrsDay, servHrsStart, servHrsBreakStart,servHrsBreakEnd,servHrsEnd FROM service_hours WHERE servId=?;', result.servId, function (err, times) {
                                    result.servHrs = times;
                                    allStaff = allStaff.concat(result);
                                    if (allStaff.length == total) {
                                        console.log('Sending all staff objects');
                                        callback(false, allStaff);
                                    }
                                });
                            })
                        },
                        function (err, results) {
                            total = results;
                            console.log("Set Total");
                        }
                    );
                });
            }


            getAllStaff();
        },
        findById: function (id, callback) {
            //Get service provider with id
            //Get service provider times
            //Merge results and return
            database.serialize(function () {
                database.get('SELECT * FROM service_provider ' +
                    'LEFT JOIN contact ' +
                    'WHERE service_provider.contId = contact.contId AND service_provider.servId = ?;', id, function (error, result) {
                    if (!error) {
                        database.all('SELECT servHrsDay, servHrsStart, servHrsBreakStart,servHrsBreakEnd,servHrsEnd' +
                            ' FROM service_hours WHERE servId = ? ORDER BY servHrsDay ASC;',
                            result.servId,
                            function (err, times) {
                                if (!err) {
                                    result.servHrs = times;
                                    callback(false, result);
                                } else {
                                    callback(true, null);
                                }
                            });
                    } else {
                        callback(true, null);
                    }
                });

            });
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
                contactModel.insert(data, function (contactInsertionError) {
                    if (!contactInsertionError) {
                        console.log('Getting Last Contact Id...')
                        database.get('SELECT contId FROM contact ORDER BY rowid DESC LIMIT 1;', function (lastInsertedIdError, results) {
                            if (!lastInsertedIdError) {
                                var statement = database.prepare(
                                    'INSERT INTO service_provider ' +
                                        '(contId, servBio, servPortrait, servInitiated, servIsActive) ' +
                                        ' VALUES (?,?,?,?,?);'
                                );
                                statement.run(
                                    [
                                        results.contId,
                                        data.servBio,
                                        data.servPortrait,
                                        data.servInitiated,
                                        (typeof data.servIsActive === 'undefined') ? 1 : data.servIsActive,
                                    ],
                                    function (providerInsertionError) {
                                        if (!providerInsertionError) {
                                            database.get('SELECT servId FROM service_provider ORDER BY rowid DESC LIMIT 1;', function (lastServiceInsertedIdError, serviceResults) {
                                                if (!lastServiceInsertedIdError) {
                                                    var stmt = database.prepare(
                                                        'INSERT INTO service_hours ' +
                                                            '(servId, servHrsDay, servHrsStart, servHrsBreakStart, servHrsBreakEnd, servHrsEnd) ' +
                                                            ' VALUES (?,?,?,?,?,?);'
                                                    );

                                                    console.log(data);
                                                    for (var i = 0; i < data.servHours.length; i++) {
                                                        stmt.run(
                                                            [
                                                                serviceResults.servId,
                                                                i + 1,
                                                                data.servHours[i].start,
                                                                data.servHours[i].break,
                                                                data.servHours[i].breakEnd,
                                                                data.servHours[i].end,
                                                            ]);
                                                    }
                                                    stmt.finalize();
                                                    callback(false, null);
                                                } else {
                                                    callback(true, null);
                                                }
                                            });
                                        } else {
                                            callback(true, null);
                                        }

                                    }
                                );
                            } else {
                                callback(true, null);
                            }

                        });

                    } else {
                        callback(true, null);
                    }
                });
            });
        }
    }
}
