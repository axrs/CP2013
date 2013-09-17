var express = require('express'),
    fs = require('fs'),
    passport = require('passport');


var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development',
    config = require('./server/config/config');
//auth = require('./config/middlewares/authorization'),

/*var models_path = __dirname + '/server/models';
fs.readdirSync(models_path).forEach(function (file) {
    require(models_path + '/' + file);
});*/
//require('./config/passport')(passport);

var app = express();
require('./server/config/express')(app, null);
require('./server/config/routes')(app, null, null);

app.listen(config.port);
console.log('Express app started on port ' + config.port);

exports = module.exports = app;