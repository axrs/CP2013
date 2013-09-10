var DatabaseItem = require('../interfaces/DatabaseItem').DatabaseItem;
var Interface = require('../interfaces/Interface').Interface;

var SQLiteController = function (database) { // implements CRUD
    var _db = database;

    this.create = function (item, callback) {
        Interface.ensureImplements(item, DatabaseItem);
        if (item.getEntityIndex() == 0) {

            var columns = item.getEntitySchema().splice(1).join(',');
            var values = item.getPropertyArray().splice(1);
            var placeHolders = '?' + Array(values.length).join(',?');
            var query = 'INSERT INTO ' + item.getEntityName() + ' (' + columns + ') VALUES(' + placeHolders + ');';

            console.log(query);
            /*
             var statement = _db.prepare(query);
             statement.run(
             [
             data.contForename,
             data.contSurname,
             (typeof data.contCompany === 'undefined') ? '' : data.contCompany,
             (typeof data.contPhone === 'undefined') ? '' : data.contPhone,
             (typeof data.contEmail === 'undefined') ? '' : data.contEmail,
             (typeof data.contAddrStreet === 'undefined') ? '' : data.contAddrStreet,
             (typeof data.contAddrSuburb === 'undefined') ? '' : data.contAddrSuburb,
             (typeof data.contAddrCity === 'undefined') ? '' : data.contAddrCity,
             (typeof data.contAddrZip === 'undefined') ? '' : data.contAddrZip,
             (typeof data.contAddrState === 'undefined') ? '' : data.contAddrState
             ],
             callback
             );
             */


        }
    }

};


module.exports.SQLiteController = SQLiteController;

/*
 module.exports = function (database) {
 return{
 all: function (callback) {
 database.all('SELECT * FROM contact ORDER BY contForename ASC, contSurname ASC;', callback);
 },
 findById: function (id, callback) {
 database.get('SELECT * FROM contact WHERE contId = ?;', id, callback);
 },
 matchName: function (name, surname, callback) {
 database.get('SELECT * FROM contact WHERE contForename = ? AND contSurname = ?;', [name, surname], callback);
 },
 count: function (callback) {
 database.get('SELECT count(*) FROM contact', null, callback);
 },
 insert: function (data, callback) {
 var statement = database.prepare(
 'INSERT INTO contact ' +
 '(contForename, contSurname, contCompany, contPhone, contEmail, ' +
 'contAddrStreet, contAddrSuburb, contAddrCity, contAddrZip, contAddrState)' +
 ' VALUES (?,?,?,?,?,' +
 '?,?,?,?,?);'
 );
 statement.run(
 [
 data.contForename,
 data.contSurname,
 (typeof data.contCompany === 'undefined') ? '' : data.contCompany,
 (typeof data.contPhone === 'undefined') ? '' : data.contPhone,
 (typeof data.contEmail === 'undefined') ? '' : data.contEmail,
 (typeof data.contAddrStreet === 'undefined') ? '' : data.contAddrStreet,
 (typeof data.contAddrSuburb === 'undefined') ? '' : data.contAddrSuburb,
 (typeof data.contAddrCity === 'undefined') ? '' : data.contAddrCity,
 (typeof data.contAddrZip === 'undefined') ? '' : data.contAddrZip,
 (typeof data.contAddrState === 'undefined') ? '' : data.contAddrState
 ],
 callback
 );
 },
 update: function (id, data, callback) {
 var statement = database.prepare(
 'UPDATE contact SET contForename = ?, contSurname = ?, contCompany = ?, contPhone = ?, contEmail = ?, ' +
 'contAddrStreet = ?, contAddrSuburb = ?, contAddrCity = ?, contAddrZip = ?, contAddrState = ? ' +
 'WHERE contId = ?;'
 );
 statement.run(
 [
 data.contForename,
 data.contSurname,
 (typeof data.contCompany === 'undefined') ? '' : data.contCompany,
 (typeof data.contPhone === 'undefined') ? '' : data.contPhone,
 (typeof data.contEmail === 'undefined') ? '' : data.contEmail,
 (typeof data.contAddrStreet === 'undefined') ? '' : data.contAddrStreet,
 (typeof data.contAddrSuburb === 'undefined') ? '' : data.contAddrSuburb,
 (typeof data.contAddrCity === 'undefined') ? '' : data.contAddrCity,
 (typeof data.contAddrZip === 'undefined') ? '' : data.contAddrZip,
 (typeof data.contAddrState === 'undefined') ? '' : data.contAddrState,
 id
 ],
 callback
 );
 }
 }
 }
 */