module.exports = function (database) {
    return{
        all: function (callback) {
            database.all('SELECT * FROM appointment_type;', callback);
        },
        findById: function (id, callback) {
            database.get('SELECT * FROM appointment_type WHERE appTypeId = ? LIMIT 1', id, callback);
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
                    data.AppTypeDescription,
                    data.appTypeDuration,
                    (typeof data.appTypeAllDay === 'undefined')? 0 : data.appTypeAllDay
                ],
                callback
            );
        },
        update: function (id, data, callback) {
            var statement = database.prepare(
                'UPDATE appointment_type SET appTypeDescription = ? appTypeDuration = ?, appTypeAllDay = ? ' +
                    'WHERE appTypeId = ?;'
            );
            statement.run(
                [
                    data.AppTypeDescription,
                    data.appTypeDuration,
                    (typeof data.appTypeAllDay === 'undefined')? 0 : data.appTypeAllDay,
                    id
                ],
                callback
            );
        }
    }
}
