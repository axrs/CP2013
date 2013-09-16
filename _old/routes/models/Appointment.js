module.exports = function (database) {
    return{
        all: function (callback) {
            database.all(
                'SELECT * FROM appointment ' +
                    'LEFT JOIN service_provider ' +
                    'LEFT JOIN contact ' +
                    'LEFT JOIN appointment_type ', callback);
        },
        findById: function (id, callback) {
            database.get('SELECT * FROM appointment ' +
                'LEFT JOIN service_provider ' +
                'LEFT JOIN contact ' +
                'LEFT JOIN appointment_type ' +
                'WHERE appId = ?', id, callback);
        },
        count: function (callback) {
            database.get('SELECT count(*) FROM appointment;', null, callback);
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
            var statement = database.prepare(
                'UPDATE appointment SET appTypeId = ?, contId = ?, servId = ?, appDate = ?, appTime = ? ' +
                    'WHERE appId = ?');
            statement.run(
                [
                    data.appTypeId,
                    data.contId,
                    data.servId,
                    data.appDate,
                    data.appTime,
                    id
                ],
                callback
            );
        },
        delete: function (id, callback) {
            var statement = database.prepare('DELETE FROM appointment WHERE appId = ?;');
            statement.run(
                [
                    id
                ],
                callback);
        }
    }
}
