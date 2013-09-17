var path = require('path'),
    rootPath = process.cwd();

module.exports = {
    root: rootPath,
    publicFolder: rootPath + '/client',
    moduleFolder: rootPath + '/server',
    port: process.env.PORT || 8080
}
