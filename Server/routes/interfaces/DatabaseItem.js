var Interface = require('./Interface').Interface;

var DatabaseItem = new Interface('DatabaseItem', [
    'getEntityName',
    'getEntityIndex',
    'getEntitySchema',
    'toJSON',
    'getPropertyArray'
]
);

module.exports.DatabaseItem = DatabaseItem;