var Provider = require('./server/models/Provider.js');

var p = new Provider();
p.setName('Alexander');
p.setSurname('Scott');


console.log(JSON.stringify(p));