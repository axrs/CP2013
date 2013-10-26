var fs = require('fs');
var passport = require('passport');
var express = require('express');
var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('./server/config/config');

//Setup the DAO
var SqliteDAOFactory = require('./server/dao/sqlite/SqliteDAOFactory.js');
require('./server/dao/DAO.js').getInstance().setFactory(new SqliteDAOFactory());

var server = express();
require('./server/config/passport')(passport);
require('./server/config/express')(server, passport);
require('./server/config/loggers')(server);
require('./server/config/authorisation')(server);

server.listen(config.port);
console.log('Express app started on port ' + config.port);

module.exports.server = server;

var controllers = __dirname + '/server/controllers';
fs.readdirSync(controllers).forEach(function (file) {
    require(controllers + '/' + file);
});