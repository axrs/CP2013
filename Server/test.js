var SQLiteController = require('./routes/controllers/SQLiteController').SQLiteController;
var ContactModel = require('./routes/models/ContactModel').ContactModel;

var model = new ContactModel();
model.setForename("Alexander");
model.setSurname("Scott");
var controller = new SQLiteController(null);

controller.create(model);