var Interface = require('./Interface').Interface;
var DAO = new Interface('DAO',
    [
        'create',
        'retrieve', 'retrieveAll', 'retrieveWhere',
        'update', 'updateAll', 'updateWhere',
        'delete', 'deleteAll', 'deleteWhere'
    ]);
module.exports.DAO = DAO;