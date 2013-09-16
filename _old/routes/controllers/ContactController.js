var Interface = require('../interfaces/Interface').Interface
    , DatabaseItem = require('../interfaces/DatabaseItem').DatabaseItem;


function ContactController(DAO, ContactModel, ContactView) { // implements CRUD

    var _model = null;
    var _view = null;

    function setModel(dao, model) {
        Interface.ensureImplements(model, DatabaseItem);
        _model = model;
        ContactModel.setDAO(dao);
    }

    function setView(view) {
        _view = view;
    }

    this.update = function (values) {

    }

    this.getAll = function (callback) {
        if (_model) {
            _model.retrieveAll(callback);
        }
        else {
            callback(true, []);
        }
    }
    setModel(DAO, ContactModel);
}

module.exports.ContactController = ContactController;