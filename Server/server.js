/**
 * Module dependencies.
 */
var express = require('express')
    , path = require('path')
    , http = require('http')
    , config = require('./config.js')
    , sqlite3 = require('sqlite3');

var db = new sqlite3.Database(config.sqlite.dbPath);

var app = exports.app = express();

/**
 * Configure the web service
 */
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

app.locals.deleteButton = require('./libs/helpers').deleteButton;

locals = function (req, res, next) {
    res.locals.res = res;
    res.locals.req = req;
    next();
};
var core = require('./routes/core.js')()
    , Contact = require('./models/contact')(db)
    , contacts = require('./routes/contact')(Contact);

var loadContact = require('./libs/middleware/route-middleware')(Contact).loadModel;

app.get('/',locals, core.index);
app.get('/contacts',locals, contacts.index);
app.get('/contacts/:id([0-9]+)',locals, loadContact('contact you requested'), contacts.show);

/**
 * Initialise the HTTP listening server
 */
http.createServer(app).listen(app.get('port'), function () {
    console.log('Server started listening on port ' + app.get('port'));
});
