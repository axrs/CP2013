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
                                        callback(false, allStaff);
                                    }
                                });
                            })
                        },
                        function (err, results) {
                            total = results;
                            if (total == 0) {
                                callback(true, allStaff);
                            }
                        }
                    );
                });
            }

            getAllStaff();
        },
        findById: function (id, callback) {


            function getStaff() {
                database.serialize(function () {

                    database.each('SELECT * FROM service_provider LEFT JOIN contact WHERE service_provider.contId = contact.contId AND service_provider.servId = ?;', id, function (err, result) {
                            database.serialize(function () {
                                database.all('SELECT servHrsDay, servHrsStart, servHrsBreakStart,servHrsBreakEnd,servHrsEnd FROM service_hours WHERE servId=?;', result.servId, function (err, times) {
                                    result.servHrs = times;
                                    callback(false, result);
                                });
                            })
                        },
                        function (err, results) {
                            if (err) {
                                callback(true, null);
                            }
                            if (!results) {
                                callback(false, null);
                            }
                        }
                    );
                });
            }

            getStaff();

        },
        count: function (callback) {
            database.get('SELECT count(*) FROM service_provider', null, callback);
        },
        update: function (id, data, callback) {

            function updateBaseContactTable(contactId, nextMethod) {
                var contactModel = require('./Contact.js')(database);
                contactModel.update(contactId, data, nextMethod);
            }

            function updateServiceProviderTable(nextMethod) {
                var statement = database.prepare(
                    'UPDATE service_provider SET servBio = ?, servPortrait = ?, servInitiated = ?, servTerminated = ? WHERE servId = ?;'
                );
                statement.run(
                    [
                        data.servBio,
                        data.servPortrait,
                        data.servInitiated,
                        data.servTerminated,
                        (typeof data.servIsActive === 'undefined') ? 1 : data.servIsActive,
                        id
                    ], nextMethod);
            }


            function deleteProviderWorkHours(nextMethod) {
                database.run('DELETE FROM service_hours WHERE servId = ?', id, nextMethod)
            }

            function insertProviderWorkHours(nextMethod) {
                var stmt = database.prepare(
                    'INSERT INTO service_hours ' +
                        '(servId, servHrsDay, servHrsStart, servHrsBreakStart, servHrsBreakEnd, servHrsEnd) ' +
                        ' VALUES (?,?,?,?,?,?);'
                );
                for (var i = 0; i < data.servHrs.length; i++) {
                    stmt.run(
                        [
                            id,
                            i,
                            data.servHrs[i].servHrsStart,
                            data.servHrs[i].servHrsBreakStart,
                            data.servHrs[i].servHrsBreakEnd,
                            data.servHrs[i].servHrsEnd,
                        ]);
                }
                stmt.finalize(nextMethod);
            }

            function updateServiceProvider() {
                database.serialize(function () {
                    {
                        updateBaseContactTable(data.contId, function (contactError, result) {
                            updateServiceProviderTable(function (providerError, result) {
                                deleteProviderWorkHours(function (deleteError) {
                                    insertProviderWorkHours(function (insertError) {
                                        callback(false, null);

                                    });
                                });
                            });
                        });
                    }
                })
            }

            updateServiceProvider();

        },


        //TODO: Cleanup this method
        insert: function (data, callback) {
            var contactModel = require('./Contact.js')(database);
            data.contCompany = 'Shear-N-Dipity';

            database.serialize(function () {
                contactModel.insert(data, function (contactInsertionError) {
                    if (!contactInsertionError) {
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

                                                    for (var i = 0; i < data.servHrs.length; i++) {
                                                        stmt.run(
                                                            [
                                                                serviceResults.servId,
                                                                i,
                                                                data.servHrs[i].servHrsStart,
                                                                data.servHrs[i].servHrsBreakStart,
                                                                data.servHrs[i].servHrsBreakEnd,
                                                                data.servHrs[i].servHrsEnd,
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
