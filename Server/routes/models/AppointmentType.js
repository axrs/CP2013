module.exports = function (database) {
    return{
        all: function (callback) {
            database.all(
                'SELECT appointment_type.appTypeId, appTypeDescription, appTypeDuration, appTypeAllDay, ' +
                    '(CASE total WHEN total THEN total ELSE 0 END) as total ' +
                    'FROM appointment_type ' +
                    'LEFT JOIN (SELECT count(*) as total, appTypeId FROM appointment GROUP BY appTypeId) as a ' +
                    ' ON appointment_type.appTypeId = a.appTypeId;', callback);
        },
        findById: function (id, callback) {
            database.get('SELECT appointment_type.appTypeId, appTypeDescription, appTypeDuration, appTypeAllDay, ' +
                '(CASE total WHEN total THEN total ELSE 0 END) as total ' +
                'FROM appointment_type ' +
                'LEFT JOIN (SELECT count(*) as total, appTypeId FROM appointment GROUP BY appTypeId) as a ' +
                ' ON appointment_type.appTypeId = a.appTypeId WHERE appointment_type.appTypeId = ?;', id, callback);
        },
        matchName: function (name, callback) {
            database.get('SELECT * FROM appointment_type WHERE appTypeDescription LIKE ? LIMIT 1;', '%' + name + '%', callback);
        },
        count: function (callback) {
            database.get('SELECT count(*) FROM appointment_type;', null, callback);
        },
        insert: function (data, callback) {
            var statement = database.prepare(
                'INSERT INTO appointment_type (appTypeDescription, appTypeDuration, appTypeAllDay) VALUES (?,?,?);'
            );
            statement.run(
                [
                    data.appTypeDescription,
                    data.appTypeDuration,
                    data.appTypeAllDay,
                ],
                callback
            );
        },
        update: function (id, data, callback) {
            var statement = database.prepare(
                'UPDATE appointment_type SET appTypeDescription = ?, appTypeDuration = ?, appTypeAllDay = ? ' +
                    'WHERE appTypeId = ?;'
            );
            statement.run(
                [
                    data.appTypeDescription,
                    data.appTypeDuration,
                    data.appTypeAllDay,
                    id
                ],
                callback
            );
        }
    }
}
