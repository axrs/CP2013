var Ring = require('ring');
var User = require('../../models/User.js');
var SqliteHelper = require('./SqliteHelper.js');
var IUserDAO = require('../IUserDAO.js');
var UUID = require('node-uuid');

var SqliteUserDAO = Ring.create([SqliteHelper, IUserDAO], {
    init: function (databaseConnection) {
        this.$super(databaseConnection);
    },
    create: function (user, callback) {
        var sql = '' +
            'INSERT INTO Contact ' +
            '(Name, MiddleName, Surname, Company, Phone, Email, Address, Suburb, City, Country, Post, State) ' +
            'VALUES ' +
            '($name, $middleName, $surname, $company, $phone, $email, $address, $suburb, $city, $country, $post, $state);';

        var values = {
            $name: user.getName(),
            $middleName: user.getMiddleName(),
            $surname: user.getSurname(),
            $company: user.getCompany(),
            $phone: user.getPhone(),
            $email: user.getEmail(),
            $address: user.getAddress(),
            $suburb: user.getSuburb(),
            $city: user.getCity(),
            $country: user.getCountry(),
            $state: user.getState(),
            $post: user.getPost()
        };

        var queryHelper = new SqliteHelper(this._db);

        queryHelper.query(sql, values, function (err, res) {
            if (err) {
                if (callback) {
                    callback(err);
                }
            } else {
                sql = 'SELECT ContactId FROM Contact ORDER BY rowid DESC LIMIT 1;';
                queryHelper.all(sql, null, function (err, result) {
                    if (err) {
                        if (callback) {
                            callback(err);
                        }
                    } else {
                        var contId = result[0].ContactId;
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
                            $contactId: contId,
                            $token: UUID.v4()
                        };
                        queryHelper.query(sql, values, function (err, res) {
                            if (callback) {
                                callback(err);
                            }
                        });
                    }
                });
            }

        });
    },

    retrieve: function (id, callback) {

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
        this.all(sql, values, function (err, result) {
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

        this.all(sql, values, function (err, result) {
            if (result.length) {
                callback(err, SqliteUserDAO.UserFromDatabase(result[0]));
            } else {
                callback(err, null);
            }
        });
    },
    lastInserted: function (callback) {
        var sql = 'SELECT * FROM User LEFT JOIN Contact WHERE User.ContactId = Contact.ContactId ORDER BY User.rowid DESC LIMIT 1;';
        this.all(sql, null, function (err, result) {
            var user = null;
            if (result.length) {
                user = SqliteUserDAO.UserFromDatabase(result[0]);
            }
            if (callback) {
                callback(err, user);
            }
        });

    },
    update: function (contact, callback) {

    },
    remove: function (id, callback) {

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