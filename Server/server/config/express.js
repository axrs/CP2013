var express = require('express'),
    config = require('./config');
var MemoryStore = express.session.MemoryStore;

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

    //CORS middleware
    var allowCrossDomain = function (req, res, next) {
        res.header('Access-Control-Allow-Origin', '*');
        res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
        res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
        res.header('Access-Control-Allow-Credentials', 'true');
        next();
    };

    server.configure(function () {
        server.use(express.cookieParser());

        server.use(express.bodyParser());
        server.use(express.methodOverride());
        server.use(allowCrossDomain);


        server.use(
            express.session({
                secret: 'CutAboveTheRest',
                maxAge: new Date(Date.now() + 6000)
            })
        );
        server.use(passport.initialize());
        server.use(passport.session());
        server.use(server.router);
    });
};