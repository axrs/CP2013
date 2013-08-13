path = require('path');

module.exports = {
    sqlite: {
        dbPath: path.resolve(__dirname, './database/shears.db')
    },
    server: {
        listenPort: 8080,
        publicFolder: path.resolve(__dirname, './public')
    }
};