path = require('path');

module.exports = {
    sqlite: {
        dbPath: path.resolve(__dirname, './database/shears.db')
    },
    mongoDB: {
        name: 'shears_db',
        host: 'localhost'
    },
    views: {
        path: path.resolve(__dirname, './views') + '/'
    },
    server: {
        listenPort: 8080,
        publicFolder: path.resolve(__dirname, './public')
    }
};