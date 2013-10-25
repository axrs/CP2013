var Ring = require('ring');
var ICRUDDAO = require('./ICRUDDAO.js');

var ITypeDAO = Ring.create([ICRUDDAO], {
});

module.exports = ITypeDAO;