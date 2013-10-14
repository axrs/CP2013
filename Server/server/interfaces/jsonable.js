var Interface = require('./Interface');

var JSONable = new Interface('JSONable',
    [
        'fromJSON', 'toJSON'
    ]
);

module.exports = JSONable;