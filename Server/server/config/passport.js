var LocalStrategy = require('passport-local').Strategy;
var GitHubStrategy = require('passport-github').Strategy;
var sqlite = require('sqlite3');
var config = require('../config/config.js');
var database = new sqlite.Database(config.db);
var DAOFactory = require('../dao/sqlite/SqliteDAOFactory.js');
var UserDAO = new DAOFactory(database).getUserDAO();

module.exports = function (passport) {
    passport.serializeUser(function (user, done) {
        done(null, user.getId());
    });

    passport.deserializeUser(function (id, done) {
        UserDAO.retrieveById(id, function (err, user) {
            done(err, user);
        });
    });

    //Use local strategy
    passport.use(new LocalStrategy({
            usernameField: 'username',
            passwordField: 'password'
        },
        function (userName, password, done) {
            UserDAO.retrieve(userName, function (err, user) {
                if (err) {
                    return done(err);
                }
                if (user == null) {
                    return done(null, false, {
                        message: 'Unknown user'
                    });
                }
                if (!user.validatePassword(password)) {
                    return done(null, false, {
                        message: 'Invalid password'
                    });
                }
                return done(null, user);
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

            /*
             UserDAO.retrieveById({
             'github.id': profile.id
             }, function (err, user) {
             if (!user) {
             user = new User({
             name: profile.displayName,
             email: profile.emails[0].value,
             username: profile.username,
             provider: 'github',
             github: profile._json
             });
             user.save(function (err) {
             if (err) console.log(err);
             return done(err, user);
             });
             } else {
             return done(err, user);
             }
             });
             */
        }
    ));

};