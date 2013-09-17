var express = require('express'),
    config = require('./config');

module.exports = function (app, passport) {
    app.set('showStackError', true);

    app.use(express.compress({
        filter: function (req, res) {
            return (/json|text|javascript|css/).test(res.getHeader('Content-Type'));
        },
        level: 9
    }));

    app.use(express.favicon());
    app.use(express.static(config.publicFolder));

    if (process.env.NODE_ENV !== 'test') {
        app.use(express.logger('dev'));
    }

    app.configure(function () {
        app.use(express.cookieParser());

        app.use(express.bodyParser());
        app.use(express.methodOverride());

        //app.use(passport.initialize());
        //app.use(passport.session());

        app.use(app.router);
    });
};