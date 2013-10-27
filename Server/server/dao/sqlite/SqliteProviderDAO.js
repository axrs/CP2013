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
        var sql = 'DELETE FROM  Provider_Hours WHERE ProviderId = $id;';
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

                database.each('SELECT * FROM Provider LEFT JOIN Contact WHERE Provider.ContactId = Contact.ContactId;', function (err, result) {
                        var provider = SqliteProviderDAO.ProviderFromDatabase(result);
                        database.serialize(function () {
                            database.all('SELECT * FROM Provider_Hours WHERE ProviderId=?;', provider.getId(), function (err, times) {
                                var hours = [];
                                if (times && times.length) {
                                    for (var i = 0; i < times.length; i++) {
                                        hours.push(SqliteProviderDAO.ProviderHoursFromDatabase(times[i]));
                                    }
                                }
                                provider.setHours(hours);
                                providers = providers.concat(provider);
                                if (providers.length == total && callback) {
                                    callback(false, providers);
                                }
                            });
                        })
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
    retrieveById: function (id, strategy, callback) {
        var sql = 'SELECT * FROM Provider LEFT JOIN Contact WHERE ProviderId=$id AND Provider.ContactId = Contact.ContactId AND isActive=1 LIMIT 1;';
        var values = {$id: id};

        var queryHelper = new SqliteHelper(this._db);

        queryHelper.all(sql, values, function (err, result) {
            if (result.length) {
                callback(err, SqliteProviderDAO.ProviderFromDatabase(result[0]));
            } else {
                callback(err, null);
            }
        });
    },
    retrieveByName: function (name, surname, callback) {
        var sql = 'SELECT * FROM Provider LEFT JOIN Contact WHERE Provider.ContactId = Contact.ContactId AND Name=$name AND Surname=$surname LIMIT 1;';

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
    update: function (provider, callback) {

        var queryHelper = new SqliteHelper(this._db);

        this.$super(provider, function (err) {
            if (err) {
                if (callback) {
                    callback(err);
                }
            } else {
                var sql = '' +
                    'UPDATE Provider SET' +
                    'Biography=$biography, Portrait=$portrait, Initiated=$initiated, Terminated=$terminated, Color=$color  ' +
                    'WHERE ProviderId=$id;';

                var values = {
                    $biography: provider.getBiography(),
                    $portrait: provider.getPortrait(),
                    $initiated: provider.getInitiated(),
                    $terminated: provider.getTerminated(),
                    $color: provider.getColor(),
                    $id: provider.getId()
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


SqliteProviderDAO.ProviderFromDatabase = function (row) {

    var provider = new Provider();

    provider.setContactId(row.ContactId);
    provider.setName(row.Name);
    provider.setMiddleName(row.MiddleName);
    provider.setSurname(row.Surname);
    provider.setCompany(row.Company);
    provider.setEmail(row.Email);
    provider.setPhone(row.Phone);
    provider.setAddress(row.Address, row.Suburb, row.City, row.Country, row.State, row.Post);
    provider.setId(row.ProviderId);
    provider.setBiography(row.Biography)
    provider.setPortrait(row.Portrait);
    provider.setInitiated(row.Initiated);
    provider.setTerminated(row.Terminated);
    provider.setColor(row.Color);

    return provider;
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