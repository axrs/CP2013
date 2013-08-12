/**
 * Description
 *
 * User: xander
 * Date: 8/12/13
 * Time: 6:37 PM
 *
 * Revisions:
 *
 */

var port = 80;

var express = require('express');
var path = require('path');
var http = require('http');
var db = require('./lib/database.js').database();

var api;

var app = express();

app.configure(function () {
    app.use(express.logger('dev'));
    app.use(express.bodyParser());
    app.use(express.static(path.join(__dirname, 'public')));
});

http.createServer(app).listen(port, function () {
    console.log('Server started listening on port ' + port);
});
