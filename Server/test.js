var MongoDAO = require('./routes/managers/MongoDAO').MongoDAO;
var ContactModel = require('./routes/models/ContactModel').ContactModel;
var ContactController = require('./routes/controllers/ContactController').ContactController;
var config = require('./config.js');
var mongoose = require('mongoose');


var conn = mongoose.createConnection('mongodb://' + config.mongoDB.host + '/' + config.mongoDB.name);

var model = new ContactModel();
model.setForename("Alexander");
model.setSurname("Scott");
var dao = new MongoDAO(conn);

var controller = new ContactController(dao, model, null);

controller.getAll(function (err, result) {
    console.log(result);
});