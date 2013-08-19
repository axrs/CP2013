/**
 * Module dependencies.
 */
var express = require('express')
    , path = require('path')
    , http = require('http')
    , config = require('./config.js');

//Initialise the express server application
var app = exports.app = express();

//Configure the server
app.configure(function () {
    app.set('port', process.env.PORT || config.server.listenPort);
    app.set('views', __dirname + '/views');
    app.set('view engine', 'ejs');
    app.use(express.favicon());
    app.use(express.logger('dev'));
    app.use(express.bodyParser());
    app.use(express.methodOverride()); // Allows use of HTML REST hacks of _method
    app.use(app.router);
    app.use(express.static(path.join(__dirname, 'public')));
});


//Connect routes
require('./routes/RouteController.js')(app);

/**
 * Initialise the HTTP listening server
 */
http.createServer(app).listen(app.get('port'), function () {
    console.log('Server started listening on port ' + app.get('port'));
});
