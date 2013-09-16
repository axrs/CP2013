var Interface = require('./Interface');

var SqliteDAO = new Interface('SqliteDAO', ['get', 'all', 'each', 'exec', 'prepare']);

module.exports = SqliteDAO;