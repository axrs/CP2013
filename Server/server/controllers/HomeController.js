server = module.exports.server = module.parent.exports.server;
server.get('/', function (req, res) {
    res.send('Shear-N-Dipity');
});
server.get('/logout', function (req, res) {
    req.logout();
    res.redirect('/');
});