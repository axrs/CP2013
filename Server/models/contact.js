/**
 * Description
 *
 * User: xander
 * Date: 8/13/13
 * Time: 6:21 PM
 *
 * Revisions:
 *
 */


module.exports = function (database) {
    return{
        all: function (callback) {
            database.all('SELECT * FROM contact;', callback);
        },
        findById: function (id, callback) {
            database.get('SELECT * FROM contact WHERE cont_id = ?;', id, callback);
        },
        count: function (callback) {
            database.get('SELECT count(*) FROM contact', null, callback);
        },
        insert: function (data, callback) {
            var statement = database.prepare(
                'INSERT INTO contact ' +
                    '(cont_forename, cont_surname, cont_company, cont_phone, cont_email)' +
                    ' VALUES (?,?,?,?,?);'
            );
            statement.run(
                [
                    data.cont_forename,
                    data.cont_surname,
                    data.cont_company,
                    data.cont_phone,
                    data.cont_email
                ],
                callback
            );
        }
    }
}
