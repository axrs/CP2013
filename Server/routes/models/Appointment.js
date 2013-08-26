module.exports = function (database) {
    return{
        getProviderAvailabilities: function (id, date, callback) {
            database.all(
                'SELECT timeSlot, servId FROM (' +
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
                        date = startDate.toISOString().substring(0, 10).replace('T', ' ');
                        console.log('Getting events for: ' + date);

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
                                    console.log(result);
                                    allEvents = allEvents.concat(result);
                                    processedDays++;
                                    console.log(processedDays + '/' + dateRange);
                                    if (processedDays == dateRange) {
                                        console.log('Got all events!');
                                        callback(false, allEvents);
                                    }
                                }
                            });
                        startDate.setDate(startDate.getDate() + 1);
                    }
                });
            }
            getEventsRange();
        }
    }
}