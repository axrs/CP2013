var uuid = require('node-uuid')
    , mongoose = require('mongoose')
    , Interface = require('../interfaces/Interface').Interface
    , DatabaseItem = require('../interfaces/DatabaseItem').DatabaseItem;

function MongoDAO(conn) {//implements DAOManager
    if (!conn) {
        throw new Error("Connection required.");
    }

    var _connection = conn;
    var _modelRegister = {};

    String.prototype.titleCase = function () {
        return this.replace(/\w\S*/g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        });
    };

    function tryRegisterSchema(modelItem) {
        Interface.ensureImplements(modelItem, DatabaseItem);
        var modelName = modelItem.getEntityName().titleCase();

        if (!_modelRegister[modelName]) {
            _modelRegister[modelName] = new mongoose.Schema(modelItem.getEntitySchema());
        }
    }

    this.create = function (modelItem, callback) {
        Interface.ensureImplements(modelItem, DatabaseItem);
        tryRegisterSchema(modelItem);

        modelItem.setEntityIndex(uuid());

        var modelName = modelItem.getEntityName().titleCase();
        var modelProperties = modelItem.getPropertyValues();
        var modelPropertyNames = modelItem.getPropertyNames();

        var MongoModel = _connection.model(modelName, _modelRegister[modelName]);
        var dbModel = new MongoModel();

        for (var i = 1; i != modelProperties.length; i++) {
            dbModel[modelPropertyNames[i]] = modelProperties[i];
        }

        dbModel.save(function (err) {
            if (err) {
                console.log('Error: create(): ' + err);
            }
            if (callback) {
                callback(err, modelItem);
            }
            return modelItem;
        });
    };

    this.updateWhere = function (modelClass, properties, callback) {
        throw new Error('Method not supported.');
    };
    this.updateAll = function (modelClass, properties, callback) {
        throw new Error('Method not supported.');
    };

    this.update = function (model, callback) {
        Interface.ensureImplements(modelItem, DatabaseItem);
        tryRegisterSchema(modelItem);

        var modelName = model.getEntityName().titleCase();
        var modelPropertiyValues = model.getPropertyValues();
        var modelPropertyNames = model.getPropertyNames();

        var updateObject = {};
        for (var i = 1; i != modelPropertiyValues.length; i++) {
            updateObject[modelPropertyNames[i]] = modelPropertiyValues[i];
        }

        var MongoModel = _connection.model(modelName, _modelRegister[modelName]);

        MongoModel.update({'_id': modelItem.getId()}, {$set: updateObject}, {}, function (err) {
            if (err) {
                console.log('Error: create(): ' + err);
            }
            if (callback) {
                callback(err, model);
            }
            return model;
        });
    };

    this.retrieve = function (modelClass, id, callback) {
        this.retrieveWhere(modelClass, {'_id': id}, callback);
    };

    this.retrieveAll = function (modelClass, callback) {
        this.retrieveWhere(modelClass, {}, callback);
    };

    this.retrieveWhere = function (modelClass, propertyNames, callback) {
        Interface.ensureImplements(modelClass, DatabaseItem);
        tryRegisterSchema(modelClass);

        var modelName = modelClass.getEntityName().titleCase();
        var MongoModel = _connection.model(modelName, _modelRegister[modelName]);

        var query = MongoModel.find(propertyNames);
        query.execFind(function (err, results) {
            if (err) {
                console.log('Error: list(): ' + err);
            }
            if (callback) {
                callback(err, results);
            }
            return results;
        });
    };


    this.delete = function (modelClass, id, callback) {
        this.deleteWhere(modelClass, {'_id': id}, callback);
    };
    this.deleteAll = function (modelClass, callback) {
        throw new Error('Method not supported.');
    };
    this.deleteWhere = function (modelClass, properties, callback) {

        console.log(properties);
        Interface.ensureImplements(modelClass, DatabaseItem);
        tryRegisterSchema(modelClass);

        var modelName = modelClass.getEntityName().titleCase();
        var MongoModel = _connection.model(modelName, _modelRegister[modelName]);

        MongoModel.remove(properties, function (err, result) {
            if (err) {
                console.log('Error: deleteWhere(): ' + err);
            }
            if (callback) {
                callback(false, result);
            }
            return result;
        });
    };

}

module.exports.MongoDAO = MongoDAO;