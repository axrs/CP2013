var Util = require("util")
    , EventEmitter = process.EventEmitter
    , instance;

function LogEventDispatcher() {
    EventEmitter.call(this);
}

Util.inherits(LogEventDispatcher, EventEmitter);

module.exports = {
    // call it getInstance, createClient, whatever you're doing
    getInstance: function () {
        return instance || (instance = new LogEventDispatcher());
    },
    log: function (message) {
        this.getInstance().emit('log', message);
    }
};