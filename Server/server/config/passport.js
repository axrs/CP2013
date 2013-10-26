var LocalStrategy = require('passport-local').Strategy;
var GitHubStrategy = require('passport-github').Strategy;
var BearerStrategy = require('passport-http-bearer').Strategy;

var sqlite = require('sqlite3');
var config = require('../config/config.js');
var database = new sqlite.Database(config.db);
var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');
var UserDAO = new DAOFactory(database).getUserDAO();
var User = require('../models/User.js');
var LogDispatcher = require('../utilities/LogEventDispatcher.js');
var UUID = require('node-uuid');

module.exports = function (passport) {
    passport.serializeUser(function (user, done) {
        LogDispatcher.log('Serialising User Session');
        done(null, user.getId());
    });

    passport.deserializeUser(function (id, done) {
        LogDispatcher.log('Deserialising User Session');
        UserDAO.retrieveById(id, 'Any', function (err, user) {
            done(err, user);
        });
    });


    passport.use(new BearerStrategy(
        function (token, done) {
            UserDAO.retrieveByToken(token, function (err, user) {
                done(err, user);
            });
        }
    ));

    //Use github strategy
    passport.use(new GitHubStrategy({
            clientID: config.github.clientID,
            clientSecret: config.github.clientSecret,
            callbackURL: config.github.callbackURL
        },
        function (accessToken, refreshToken, profile, done) {
            UserDAO.retrieveById(profile.id, 'github', function (err, user) {
                if (user == null) {
                    user = new User();

                    if (profile._json.name) {
                        var names = profile._json.name.split(" ");
                        user.setName(names[0]);
                        if (names.length > 1) {
                            user.setSurname(names[names.length - 1]);
                        }
                        if (names.length > 2) {
                            user.setMiddleName(names[1]);
                        }
                    } else {
                        user.setName(profile.username);
                        user.setSurname('GitHub User');
                    }

                    user.setStrategyId(profile.id);
                    user.setStrategy('github');
                    user.setStrategyData(JSON.stringify(profile));
                    user.setEmail(profile.emails[0].value);
                    user.setCompany(profile._json.company);


                    LogDispatcher.log('Creating new GitHub User.');

                    UserDAO.create(user, function (err) {
                        if (err) {
                            return done(err, null);
                        }
                        else {
                            UserDAO.lastInserted(function (error, result) {
                                return done(err, result);
                            });
                        }
                    });
                } else {
                    LogDispatcher.log('Existing GitHub User Logged In.');
                    return done(err, user);
                }
            });
        }
    ));
};