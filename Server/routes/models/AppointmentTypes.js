module.exports = function (database) {
    return{
        all: function (callback) {
            database.all('', callback);
        },
        findById: function (id, callback) {
            database.get('', id, callback);
        },
        matchName: function (name, callback) {
            database.get('', [name], callback);
        },
        count: function (callback) {
            database.get('', null, callback);
        },
        insert: function (data, callback) {
            var statement = database.prepare(
                ''
            );
            statement.run(
                [
                ],
                callback
            );
        },
        update: function (id, data, callback) {
            var statement = database.prepare(
                ''
            );
            statement.run(
                [

                ],
                callback
            );
        }
    }
}
