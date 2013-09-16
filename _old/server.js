/**
 * Module dependencies.
 */
var express = require('express')
    , path = require('path')
    , http = require('http')
    , config = require('./config.js')
    , expressValidator = require('express-validator')
    , mongoose = require('mongoose');

//Initialise the express server application
var app = exports.app = express();

//Configure the server
app.configure(function () {
    app.set('port', process.env.PORT || config.server.listenPort);
    app.set('views', __dirname + '/views');
    app.set('view engine', 'ejs');
    app.use(express.favicon());
    app.use(express.logger('dev'));
    app.use(express.bodyParser())
    app.use(expressValidator());  //required for Express-Validator
    app.use(express.methodOverride()); // Allows use of HTML REST hacks of _method
    app.use(app.router);
    app.use(express.static(path.join(__dirname, 'public')));
});


/**
 * Initialise the HTTP listening server
 */
http.createServer(app).listen(app.get('port'), function () {
    console.log('Server started listening on port ' + app.get('port'));
});

/*
 * Exports the express app for other modules to use
 * all route matches go the routes.js file
 */
module.exports.app = app;
module.exports.app.config = config;
module.exports.app.mongoDBConnection = mongoose.createConnection('mongodb://' + config.mongoDB.host + '/' + config.mongoDB.name);

module.exports.app.exposeLocals = function (req, res, next) {
    res.locals.res = res;
    res.locals.req = req;
    res.viewPath = config.views.path;
    res.statusCodes = require('./libs/StatusHelpers.js');
    next();
};

//Connect routes
//require('./routes/RouteController.js');