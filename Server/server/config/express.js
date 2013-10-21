var express = require('express'),
    config = require('./config');
var MemoryStore = express.session.MemoryStore;
var sessionStore = new MemoryStore();
module.exports = function (server, passport) {
    server.set('showStackError', true);

    server.use(express.compress({
        filter: function (req, res) {
            return (/json|text|javascript|css/).test(res.getHeader('Content-Type'));
        },
        level: 9
    }));

    server.use(express.favicon());

    if (process.env.NODE_ENV !== 'test') {
        server.use(express.logger('dev'));
    }

    server.configure(function () {
        server.use(express.cookieParser());

        server.use(express.bodyParser());
        server.use(express.methodOverride());


        server.use(
            express.session({
                secret: 'CutAboveTheRest',
                maxAge: new Date(Date.now() + 3600000)
            })
        );
        server.use(passport.initialize());
        server.use(passport.session());

        server.use(server.router);
    });
};