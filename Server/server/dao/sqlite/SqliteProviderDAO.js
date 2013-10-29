var Ring = require('ring');
var Provider = require('../../models/Provider.js');
var ProviderHours = require('../../models/ProviderHours.js');

var SqliteHelper = require('./SqliteHelper.js');
var SqliteContactDAO = require('./SqliteContactDAO.js');
var IProviderDAO = require('../IProviderDAO.js');

var SqliteProviderDAO = Ring.create([IProviderDAO, SqliteContactDAO], {
    init: function (databaseConnection) {
        this.$super(databaseConnection);
    },
    create: function (provider, callback) {

        var queryHelper = new SqliteHelper(this._db);
        var that = this;

        this.$super(provider, function (err) {
            if (err) {
                if (callback) {
                    callback(err);
                }
            } else {
                that._lastInsertedContactId(function (error, id) {
                    if (error) {
                        if (callback) {
                            callback(error);
                        }
                    } else {
                        var sql = '' +
                            'INSERT INTO Provider ' +
                            '(ContactId, Biography, Portrait, Initiated, Terminated, Color) ' +
                            'VALUES ' +
                            '($contactId, $biography, $portrait, $initiated, $terminated, $color);';

                        var values = {
                            $contactId: id,
                            $biography: provider.getBiography(),
                            $portrait: provider.getPortrait(),
                            $initiated: provider.getInitiated(),
                            $terminated: provider.getTerminated(),
                            $color: provider.getColor()
                        };
                        queryHelper.query(sql, values, function (err) {
                            if (callback) {
                                callback(err);
                            }
                        });
                    }
                });
            }
        });
    },
    updateProviderHours: function (id, hours, callback) {
        var database = this._db;

        var stmt = database.prepare(
            'INSERT INTO Provider_Hours ' +
                '(ProviderId, Day, Start, BreakStart, BreakEnd, End) ' +
                ' VALUES (?,?,?,?,?,?);'
        );
        for (var i = 0; i < hours.length; i++) {
            var ph = hours[i];
            stmt.run(
                [
                    id,
                    ph.getDay(),
                    ph.getStart(),
                    ph.getBreakStart(),
                    ph.getBreakEnd(),
                    ph.getEnd(),
                ]);
        }
        stmt.finalize(function (err) {
            if (callback) {
                callback(err);
            }
        });

    },
    removeProviderHours: function (id, callback) {
        var sql = 'DELETE FROM Provider_Hours WHERE ProviderId = $id;';
        var queryHelper = new SqliteHelper(this._db);

        queryHelper.query(sql, {$id: id}, function (err, result) {
            if (callback) {
                callback(err, result);
            }
        });
    },

    retrieveAll: function (callback) {

        var database = this._db;
        var providers = [];
        var total = 0;

        function getAll() {
            database.serialize(function () {

                database.each('SELECT * FROM Provider LEFT JOIN Contact ON Provider.ContactId = Contact.ContactId AND Provider.isActive=1;', function (err, result) {
                        SqliteProviderDAO.ProviderFromDatabase(result, function (res2) {
                            database.serialize(function () {
                                database.all('SELECT * FROM Provider_Hours WHERE ProviderId=?;', res2.getId(), function (err, times) {
                                    var hours = [];
                                    if (times && times.length) {
                                        for (var i = 0; i < times.length; i++) {
                                            hours.push(SqliteProviderDAO.ProviderHoursFromDatabase(times[i]));
                                        }
                                    }
                                    res2.setHours(hours);
                                    providers = providers.concat(res2);
                                    if (providers.length == total && callback) {
                                        callback(false, providers);
                                    }
                                });
                            })
                        });
                    },
                    function (err, results) {
                        total = results;
                        if (total == 0) {
                            callback(true, providers);
                        }
                    }
                );
            });
        }

        getAll();

    },
    retrieveProviderHours: function (id, callback) {
        var sql = 'SELECT * FROM Provider_Hours WHERE ProviderId=$id;';
        this.all(sql, {$id: id}, function (err, result) {
            var hours = [];
            if (result && result.length) {
                for (var i = 0; i < result.length; i++) {
                    hours.push(SqliteProviderDAO.ProviderHoursFromDatabase(result[i]));
                }
            }
            if (callback) {
                callback(err, hours);
            }
        });
    },
    retrieveById: function (id, callback) {
        var database = this._db;

        database.serialize(function () {

            database.each('SELECT * FROM Provider LEFT JOIN Contact ON Provider.ContactId = Contact.ContactId WHERE Provider.ProviderId=? AND Provider.isActive=1 LIMIT 1;', id, function (err, result) {
                    var providerResult = SqliteProviderDAO.ProviderFromDatabase(result);
                    database.serialize(function () {
                        database.all('SELECT * FROM Provider_Hours WHERE ProviderId=?;', id, function (err, times) {
                            var hours = [];
                            if (times && times.length) {
                                for (var i = 0; i < times.length; i++) {
                                    hours.push(SqliteProviderDAO.ProviderHoursFromDatabase(times[i]));
                                }
                            }
                            providerResult.setHours(hours);
                            if (callback) {
                                callback(false, providerResult);
                            }
                        });
                    })
                }
            );
        });


    },
    retrieveByName: function (name, surname, callback) {
        var sql = 'SELECT * FROM Provider LEFT JOIN Contact ON Provider.ContactId = Contact.ContactId WHERE Name=$name AND Surname=$surname LIMIT 1;';

        this.all(sql, {$name: name, $surname: surname}, function (err, result) {
            var provider = null;
            if (result.length) {
                provider = SqliteProviderDAO.ProviderFromDatabase(result[0]);
            }
            if (callback) {
                callback(err, provider);
            }
        });
    },
    lastInserted: function (callback) {
        var sql = 'SELECT * FROM Provider LEFT JOIN Contact ON Provider.ContactId = Contact.ContactId ORDER BY Provider.rowid DESC LIMIT 1;';
        var queryHelper = new SqliteHelper(this._db);

        queryHelper.all(sql, null, function (err, result) {
            var user = null;
            if (result.length) {
                user = SqliteProviderDAO.ProviderFromDatabase(result[0]);
            }
            if (callback) {
                callback(err, user);
            }
        });

    },
    remove: function (id, callback) {
        var sql = 'UPDATE Provider SET isActive=0 WHERE ProviderId=$id;';
        this.query(sql, {$id: id}, function (err) {
            if (callback) {
                callback(err);
            }
        });
    },
    update: function (updateProvider, callback) {

        var queryHelper = new SqliteHelper(this._db);

        this.$super(updateProvider, function (err) {
            if (err) {
                if (callback) {
                    callback(err);
                }
            } else {
                var sql = '' +
                    'UPDATE Provider SET ' +
                    'Biography=$biography, Portrait=$portrait, Initiated=$initiated, Terminated=$terminated, Color=$color ' +
                    'WHERE ProviderId=$id;';

                var values = {
                    $biography: updateProvider.getBiography(),
                    $portrait: updateProvider.getPortrait(),
                    $initiated: updateProvider.getInitiated(),
                    $terminated: updateProvider.getTerminated(),
                    $color: updateProvider.getColor(),
                    $id: updateProvider.getId()
                };
                queryHelper.query(sql, values, function (err) {
                    if (callback) {
                        callback(err);
                    }
                });
            }
        });
    }
});


SqliteProviderDAO.ProviderFromDatabase = function (row, callback) {

    var p = new Provider();
    p.setContactId(row.ContactId);
    p.setName(row.Name);
    p.setMiddleName(row.MiddleName);
    p.setSurname(row.Surname);
    p.setCompany(row.Company);
    p.setEmail(row.Email);
    p.setPhone(row.Phone);
    p.setAddress(row.Address, row.Suburb, row.City, row.Country, row.State, row.Post);
    p.setId(row.ProviderId);
    p.setBiography(row.Biography)
    p.setPortrait(row.Portrait);
    p.setInitiated(row.Initiated);
    p.setTerminated(row.Terminated);
    p.setColor(row.Color);
    if (callback) {
        callback(p);
    } else {
        return p;
    }
};


SqliteProviderDAO.ProviderHoursFromDatabase = function (row) {

    var hours = new ProviderHours();

    hours.setDay(row.Day);
    hours.setStart(row.Start);
    hours.setEnd(row.End);
    hours.setBreakStart(row.BreakStart);
    hours.setBreakEnd(row.BreakEnd);

    return hours;
};

module.exports = SqliteProviderDAO;