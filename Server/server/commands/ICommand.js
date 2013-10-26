var Ring = require('ring');

/**
 * Command Interface
 * @type {class}
 * @interface
 */
var ICommand = Ring.create({

    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        throw new Error('Method Not Implemented.');
    }
});

module.exports = ICommand;

