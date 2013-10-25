var passport = require('passport');
server = module.exports.server = module.parent.exports.server;

server.get('/api/token',
    server.requiresLogin,
    function (req, res) {
        res.writeHead(200, { 'Content-Type': 'text/html' });
        res.write('<html><head><title>' + req.user.getToken() + '</title></head>' +
            '<body><script>' +
            'window.onload=function(){window.opener.postMessage("' + req.user.getToken() + '", "http://10.100.0.136:8080");};' +
            '</script></body>' +
            '</html>');
        res.end();
    }
);
server.get('/api/user',
    passport.authenticate('bearer', { session: false }),
    server.requiresLogin,
    function (req, res) {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.write(JSON.stringify(req.user));
        res.end();
    }
);
