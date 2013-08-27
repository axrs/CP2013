module.exports = function (database) {
    return{
        all: function (callback) {
            database.all('', callback);
        },
        findById: function (id, callback) {
            database.get('', id, callback);
        },
        matchName: function (name, surname, callback) {
            database.get('', [name, surname], callback);
        },
        count: function (callback) {
            database.get('', null, callback);
        },
        insert: function (data, callback) {
            var statement = database.prepare('');
            statement.run(
                [
                ],
                callback
            );
        },
        update: function (id, data, callback) {
            var statement = database.prepare('');
            statement.run(
                [
                ],
                callback
            );
        }
    }
}
