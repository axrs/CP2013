var Ring = require('ring');
var User = require('../../models/User.js');
var SqliteHelper = require('./SqliteHelper.js');
var IUserDAO = require('../IUserDAO.js');
var UUID = require('node-uuid');
var SqliteContactDAO = require('./SqliteContactDAO.js');

var SqliteUserDAO = Ring.create([IUserDAO, SqliteContactDAO], {
    init: function (databaseConnection) {
        this.$super(databaseConnection);
    },
    create: function (user, callback) {

        var queryHelper = new SqliteHelper(this._db);
        var that = this;

        this.$super(user, function (err) {
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
                            'INSERT INTO User ' +
                            '(User, Password, isAdmin, StrategyId, Strategy, StrategyData, ContactId, Token) VALUES ' +
                            '($user, $password, $isAdmin, $strategyId, $strategy, $strategyData, $contactId, $token);';

                        var values = {
                            $user: user.getUserName(),
                            $password: user.getPassword(),
                            $isAdmin: user.getIsAdmin(),
                            $strategyId: user.getStrategyId(),
                            $strategy: user.getStrategy(),
                            $strategyData: user.getStrategyData(),
                            $contactId: id,
                            $token: UUID.v4()
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

    retrieveById: function (id, strategy, callback) {
        if (strategy == null) {
            strategy = 'local';
        }
        var sql = 'SELECT * FROM User LEFT JOIN Contact WHERE UserId=$id AND User.ContactId = Contact.ContactId AND Strategy=$strategy AND isActive=1 LIMIT 1;';
        var values = {$id: id, $strategy: strategy};

        if (strategy != 'local') {
            sql = 'SELECT * FROM User LEFT JOIN Contact WHERE StrategyId=$id AND User.ContactId = Contact.ContactId AND Strategy=$strategy AND isActive=1 LIMIT 1;';
        }
        if (strategy == 'Any') {
            sql = 'SELECT * FROM User LEFT JOIN Contact WHERE UserId=$id AND User.ContactId = Contact.ContactId AND isActive=1 LIMIT 1;';
            values = {$id: id};
        }
        var queryHelper = new SqliteHelper(this._db);

        queryHelper.all(sql, values, function (err, result) {
            if (result.length) {
                if (callback) {
                    callback(err, SqliteUserDAO.UserFromDatabase(result[0]));
                }
            } else {
                if (callback) {
                    callback(err, null);

                }
            }
        });
    },
    retrieveByUserName: function (username, callback) {
        var sql = 'SELECT * FROM User LEFT JOIN Contact WHERE UserName=$username AND User.ContactId = Contact.ContactId LIMIT 1;';
        var values = {$username: username};
        var queryHelper = new SqliteHelper(this._db);

        queryHelper.all(sql, values, function (err, result) {
            if (result.length) {
                callback(err, SqliteUserDAO.UserFromDatabase(result[0]));
            } else {
                callback(err, null);
            }
        });
    },
    retrieveByToken: function (token, callback) {
        var sql = 'SELECT * FROM User LEFT JOIN Contact WHERE Token=$token AND User.ContactId = Contact.ContactId AND isActive=1 LIMIT 1;';
        var values = {$token: token};
        var queryHelper = new SqliteHelper(this._db);

        queryHelper.all(sql, values, function (err, result) {
            if (result.length) {
                callback(err, SqliteUserDAO.UserFromDatabase(result[0]));
            } else {
                callback(err, null);
            }
        });
    },
    lastInserted: function (callback) {
        var sql = 'SELECT * FROM User LEFT JOIN Contact WHERE User.ContactId = Contact.ContactId ORDER BY User.rowid DESC LIMIT 1;';
        var queryHelper = new SqliteHelper(this._db);

        queryHelper.all(sql, null, function (err, result) {
            var user = null;
            if (result.length) {
                user = SqliteUserDAO.UserFromDatabase(result[0]);
            }
            if (callback) {
                callback(err, user);
            }
        });

    },
    update: function (user, callback) {
        var queryHelper = new SqliteHelper(this._db);

        this.$super(user, function (err) {
            if (err) {
                if (callback) {
                    callback(err);
                }
            } else {
                var sql = '' +
                    'UPDATE User SET' +
                    'Password=$password, isAdmin=$isAdmin, StrategyData=$strategyData ' +
                    'WHERE UserId=$id;';

                var values = {
                    $password: user.getPassword(),
                    $isAdmin: user.getIsAdmin(),
                    $strategyData: user.getStrategyData(),
                    $id: user.getId()
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


SqliteUserDAO.UserFromDatabase = function (row) {
    var user = new User();
    user.setContactId(row.ContactId);
    user.setName(row.Name);
    user.setMiddleName(row.MiddleName);
    user.setSurname(row.Surname);
    user.setCompany(row.Company);
    user.setEmail(row.Email);
    user.setPhone(row.Phone);
    user.setAddress(row.Address, row.Suburb, row.City, row.Country, row.State, row.Post);

    user.setUserName(row.User);
    user.setId(row.UserId);
    user.setHashedPassword(row.Password);
    user.setIsAdmin(row.isAdmin);
    user.setStrategyId(row.StrategyId);
    user.setStrategy(row.Strategy);
    user.setStrategyData(row.StrategyData);
    user.setToken(row.Token);

    return user;
};

module.exports = SqliteUserDAO;