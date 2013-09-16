var Interface = require('./Interface').Interface;
var CRUD = new Interface('CRUD', ['create', 'retrieveAll', 'retrieve', 'update', 'delete']);
module.exports.DatabaseItem = CRUD;