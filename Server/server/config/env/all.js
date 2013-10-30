var os = require('os');

var interfaces = os.networkInterfaces();
var addresses = [];
for (k in interfaces) {
    for (k2 in interfaces[k]) {
        var address = interfaces[k][k2];
        if (address.family == 'IPv4' && !address.internal) {
            addresses.push(address.address)
        }
    }
}

var rootPath = process.cwd();

module.exports = {
    root: rootPath,
    publicFolder: rootPath + '/client',
    port: process.env.PORT || 8081,
    webLocation: 'http://10.100.0.136:8080/'
}
