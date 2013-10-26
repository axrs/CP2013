var Restify = require('restify');
var Util = require('util');

var server = Restify.createServer({
    formatters: {
        'application/json': function formatFoo(req, res, body) {
            if (body instanceof Error)
                return body.stack;
            if (Buffer.isBuffer(body))
                return body.toString('base64');
            return Util.inspect(body);
        }
    }
});
server.use(Restify.acceptParser(server.acceptable));
server.use(Restify.authorizationParser());
server.use(Restify.dateParser());
server.use(Restify.queryParser());
server.use(Restify.jsonp());
server.use(Restify.gzipResponse());
server.use(Restify.bodyParser());
server.use(Restify.throttle({
    burst: 100,
    rate: 50,
    ip: true,
    overrides: {
        '127.0.0.1': {
            rate: 0,        // unlimited
            burst: 0
        }
    }
}));
server.use(Restify.conditionalRequest());

Restify.CORS.ALLOW_HEADERS.push('accept');
Restify.CORS.ALLOW_HEADERS.push('sid');
Restify.CORS.ALLOW_HEADERS.push('lang');
Restify.CORS.ALLOW_HEADERS.push('origin');
Restify.CORS.ALLOW_HEADERS.push('withcredentials');
Restify.CORS.ALLOW_HEADERS.push('x-requested-with');
server.use(Restify.CORS());
server.listen(8081, function () {
    console.log('%s listening at %s', server.name, server.url);
});

module.exports = server;