module.exports = function (database) {
    return{
        all: function (callback) {
            database.all(
                'SELECT * FROM appointment ' +
                'LEFT JOIN service_provider ' +
                'LEFT JOIN contact' +
                'LEFT JOIN appointment_type', callback);
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
            var statement = database.prepare('INSERT INTO appointment (appTypeId, contId, servId, appDate, appTime) ' +
                'VALUES(?,?,?,?,?);');
            statement.run(
                [
                    data.appTypeId,
                    data.contId,
                    data.servId,
                    data.appDate,
                    data.appTime,
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
