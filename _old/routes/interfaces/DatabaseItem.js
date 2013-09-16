var Interface = require('./Interface').Interface;

var DatabaseItem = new Interface('DatabaseItem', [
    'getEntityName',
    'getEntityIndex',
    'setEntityIndex',
    'getEntitySchema',
    'toJSON',
    'getPropertyValues',
    'getPropertyNames',
    'setDAO'
]
);

module.exports.DatabaseItem = DatabaseItem;