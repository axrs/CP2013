var Ring = require('ring');
var AbstractTypeCommand = require('./AbstractTypeCommand.js');
var StatusHelpers = require('../../helpers/StatusHelpers.js');

var GetTypeCommand = Ring.create([AbstractTypeCommand], {

    _id: 0,

    init: function (id, dao) {
        this.$super(dao);
        this._id = id;
    },
    /**
     *
     * @param req Requester
     * @param res Response
     */
    execute: function (req, res) {
        if (this._id <= 0) {
            StatusHelpers.status409(req, res);
        } else {
            this._dao.retrieveById(this._id, function (err, results) {
                if (err) {
                    StatusHelpers.status500(req, res);
                } else {
                    res.writeHead(200, { 'Content-Type': 'application/json' });
                    res.write(JSON.stringify(results));
                    res.end();
                }
            });
        }

    }
});

module.exports = GetTypeCommand;