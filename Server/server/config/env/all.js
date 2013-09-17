var path = require('path'),
    rootPath = path.normalize(__dirname + '/../../..');

module.exports = {
    root: rootPath,
    publicFolder: rootPath + '/client',
    port: process.env.PORT || 8080
}
