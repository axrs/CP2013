function ConsoleLogger() { // extends Logger
    this.log = function (message) {
        console.log(message);
    }
}
module.exports = ConsoleLogger;
