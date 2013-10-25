var Ring = require('ring');
var IContactDAO = require('./IContactDAO.js');

var IProviderDAO = Ring.create([IContactDAO], {

});

module.exports = IProviderDAO;
