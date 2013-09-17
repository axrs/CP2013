var rootPath = process.cwd();

module.exports = {
    root: rootPath,
    publicFolder: rootPath + '/client',
    port: process.env.PORT || 8080
}
