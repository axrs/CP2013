module.exports = function (database) {
    return{
        getProviderAvailabilities: function (id, date, callback) {
            database.all(
                'SELECT timeSlot, a.servId, servColor FROM (' +
                    '    SELECT timeSlot, servId, count(*) as occurances ' +
                    '    FROM ( ' +
                    '            SELECT time(servHrsStart) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?) ' +
                    '        UNION ALL ' +
                    '            SELECT time(servHrsBreakStart) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?) ' +
                    '        UNION ALL ' +
                    '            SELECT time(servHrsBreakEnd) as timeSlot , servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?) ' +
                    '        UNION ALL ' +
                    '           SELECT time(servHrsEnd) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?) ' +
                    '        UNION ALL ' +
                    '           SELECT time(appTime) as timeSlot, servId ' +
                    '           FROM appointment as a ' +
                    '               LEFT JOIN appointment_type as b ON b.appTypeId = a.appTypeId ' +
                    '           WHERE servId = ? AND appDate = ? ' +
                    '       UNION ALL ' +
                    '           SELECT time(strftime(\'%s\',appTime) + strftime(\'%s\',appTypeDuration), \'unixepoch\') as timeSlot, servId ' +
                    '           FROM appointment as a ' +
                    '               LEFT JOIN appointment_type as b ON b.appTypeId = a.appTypeId ' +
                    '           WHERE servId = ? AND appDate = ? ' +
                    '    )' +
                    '    GROUP BY timeSlot ' +
                    '    ORDER BY timeSlot ' +
                    ') AS a LEFT JOIN service_provider ON service_provider.servId = a.servId ' +
                    'WHERE occurances = 1;',
                [
                    id, date, //servHrsStart
                    id, date, //servHrsBreakStart
                    id, date, //servHrsBreakEnd
                    id, date,  //servHrsEnd
                    id, date,  //appTime
                    id, date  //appEndTime
                ]
                , callback
            );
        },

        getProviderAvailabilitiesRange: function (id, start, end, callback) {
            var allEvents = [];
            var processedDays = 0;

            var startDate = new Date(start);
            var endDate = new Date(end)
            var dateRange = (new Date(end) - startDate) / (1000 * 60 * 60 * 24);

            function getEventsRange() {
                database.serialize(function () {

                    var date = '';
                    while (startDate < endDate) {
                        date = startDate.getFullYear() + '-' + ("0" + (startDate.getMonth() + 1)).slice(-2) + '-' + ("0" + startDate.getDate()).slice(-2);

                        database.all(
                            'SELECT timeSlot, a.servId, servColor, ? as date ' +
                            'FROM ( ' +
                            '    SELECT timeSlot, servId,  count(*) as occurances ' +
                            '    FROM ( ' +
                            '        SELECT time(servHrsStart) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?) ' +
                            '    UNION ALL ' +
                            '        SELECT time(servHrsBreakStart) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?) ' +
                            '    UNION ALL ' +
                            '        SELECT time(servHrsBreakEnd) as timeSlot , servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?) ' +
                            '    UNION ALL ' +
                            '        SELECT time(servHrsEnd) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?) ' +
                            '    UNION ALL ' +
                            '        SELECT time(appTime) as timeSlot, servId ' +
                            '        FROM appointment as a' +
                            '        LEFT JOIN appointment_type as b ON b.appTypeId = a.appTypeId ' +
                            '        WHERE servId = ? AND appDate = ? ' +
                            '    UNION ALL ' +
                            '        SELECT time(strftime(\'%s\',appTime) + strftime(\'%s\',appTypeDuration), \'unixepoch\') as timeSlot, servId ' +
                            '        FROM appointment as a ' +
                            '        LEFT JOIN appointment_type as b ON b.appTypeId = a.appTypeId ' +
                            '        WHERE servId = ? AND appDate = ? ' +
                            '    ) ' +
                            '    GROUP BY timeSlot ' +
                            '    ORDER BY timeSlot ' +
                            ') as a ' +
                            'LEFT JOIN service_provider ON service_provider.servId = a.servId ' +
                            'WHERE occurances = 1;',
                        [
                                date,
                                id, date, //servHrsStart
                                id, date, //servHrsBreakStart
                                id, date, //servHrsBreakEnd
                                id, date,  //servHrsEnd
                                id, date,  //appTime
                                id, date  //appEndTime
                            ]
                            , function (err, result) {
                                if (err) {
                                    callback(true, allEvents);
                                } else {
                                    allEvents = allEvents.concat(result);
                                    processedDays++;
                                    if (processedDays == dateRange) {
                                        callback(false, allEvents);
                                    }
                                }
                            });
                        startDate.setDate(startDate.getDate() + 1);
                    }
                });
            }

            getEventsRange();
        },
        getAllProviderAvailabilitiesRange: function (start, end, callback) {
            var events = [];
            var total = 0;

            var iteration = 0;
            var endDate = new Date(end);
            var dateRange = (new Date(end) - new Date(start)) / (1000 * 60 * 60 * 24);

            var expectedIterations = 0;

            function getEventsRange() {
                database.serialize(function () {

                    database.each('SELECT servId, servColor FROM service_provider;', function (err, row) {
                            database.serialize(function () {
                                var startDate = new Date(start);
                                var date = '';
                                while (startDate < endDate) {
                                    date = startDate.getFullYear() + '-' + ("0" + (startDate.getMonth() + 1)).slice(-2) + '-' + ("0" + startDate.getDate()).slice(-2);
                                    database.all(
                                        'SELECT timeSlot, servId, ? as date FROM (' +
                                            '    SELECT timeSlot, servId,  count(*) as occurances' +
                                            '    FROM (' +
                                            '            SELECT time(servHrsStart) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?)' +
                                            '        UNION ALL' +
                                            '            SELECT time(servHrsBreakStart) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?)' +
                                            '        UNION ALL' +
                                            '            SELECT time(servHrsBreakEnd) as timeSlot , servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?)' +
                                            '        UNION ALL' +
                                            '           SELECT time(servHrsEnd) as timeSlot, servId FROM service_hours WHERE servId = ? and servHrsDay = strftime(\'%w\',?)' +
                                            '        UNION ALL' +
                                            '           SELECT time(appTime) as timeSlot, servId' +
                                            '           FROM appointment as a' +
                                            '               LEFT JOIN appointment_type as b ON b.appTypeId = a.appTypeId' +
                                            '           WHERE servId = ? AND appDate = ?' +
                                            '       UNION ALL' +
                                            '           SELECT time(strftime(\'%s\',appTime) + strftime(\'%s\',appTypeDuration), \'unixepoch\') as timeSlot, servId' +
                                            '           FROM appointment as a' +
                                            '               LEFT JOIN appointment_type as b ON b.appTypeId = a.appTypeId' +
                                            '           WHERE servId = ? AND appDate = ?' +
                                            '    )' +
                                            '    GROUP BY timeSlot' +
                                            '    ORDER BY timeSlot' +
                                            ')' +
                                            'WHERE occurances = 1;',
                                        [
                                            date,
                                            row.servId, date, //servHrsStart
                                            row.servId, date, //servHrsBreakStart
                                            row.servId, date, //servHrsBreakEnd
                                            row.servId, date,  //servHrsEnd
                                            row.servId, date,  //appTime
                                            row.servId, date  //appEndTime
                                        ]
                                        , function (err, result) {
                                            if (err) {
                                                callback(true, events);
                                            } else {
                                                for (var j = 0; j < result.length; j++) {
                                                    result[j].servColor = row.servColor;
                                                }
                                                events = events.concat(result);
                                                iteration++;
                                                if (iteration == expectedIterations) {
                                                    callback(false, events);
                                                }
                                            }
                                        });
                                    startDate.setDate(startDate.getDate() + 1);
                                }
                            });
                        },
                        function (err, results) {
                            expectedIterations = results * dateRange;
                            if (expectedIterations == 0) {
                                callback(true, events);
                            }
                        }
                    );
                });
            }

            getEventsRange();
        }
    }
}