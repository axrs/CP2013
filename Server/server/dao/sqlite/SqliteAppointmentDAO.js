var Ring = require('ring');
var Appointment = require('../../models/AppointmentType.js');
var SqliteHelper = require('./SqliteHelper.js');
var IAppointmentDAO = require('../IAppointmentDAO.js');
var Availability = require('../../models/Availability.js');

var SqliteAppointmentDAO = Ring.create([SqliteHelper, IAppointmentDAO], {
        _oneDay: 24 * 60 * 60 * 1000,
        init: function (databaseConnection) {
            this.$super(databaseConnection);
        },

        create: function (appointment, callback) {
            var database = this._db;
            var sql = '' +
                'INSERT INTO Appointment ' +
                '(TypeId, ContactId, ProviderId, Date, Time) ' +
                'VALUES ' +
                '($type, $contact, $provider, $date, $time);';

            var values = {
                $type: appointment.getTypeId(),
                $contact: appointment.getContactId(),
                $provider: appointment.getProviderId(),
                $date: appointment.getDate(),
                $time: appointment.getTime()
            };

            database.query(sql, values, function (err) {
                if (callback) {
                    callback(err);
                }
            });
        },
        retrieveAll: function (callback) {
            var sql = 'SELECT * FROM Appointment;';
            this.all(sql, null, function (err, result) {
                var appointments = [];
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        appointments.push(SqliteAppointmentDAO.AppointmentFromDatabase(result[i]));
                    }
                }
                if (callback) {
                    callback(err, appointments);
                }
            });
        },
        retrieveById: function (id, callback) {
            var sql = 'SELECT * FROM Appointment WHERE AppointmentId=$id LIMIT 1;';
            this.all(sql, {$id: id}, function (err, result) {
                var appointment = null;
                if (result && result.length) {
                    appointment = SqliteAppointmentDAO.AppointmentFromDatabase(result);
                }
                if (callback) {
                    callback(err, appointment);
                }
            });
        },
        retrieveForProvider: function (providerId, callback) {
            var sql = 'SELECT * FROM Appointment WHERE ProviderId=$id;';
            this.all(sql, {$id: providerId}, function (err, result) {
                var appointments = [];
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        appointments.push(SqliteAppointmentDAO.AppointmentFromDatabase(result[i]));
                    }
                }
                if (callback) {
                    callback(err, appointments);
                }
            });
        },

        retrieveForContact: function (contactID, callback) {
            var sql = 'SELECT * FROM Appointment WHERE ContactId=$id;';
            this.all(sql, {$id: contactID}, function (err, result) {
                var appointments = [];
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        appointments.push(SqliteAppointmentDAO.AppointmentFromDatabase(result[i]));
                    }
                }
                if (callback) {
                    callback(err, appointments);
                }
            });
        },
        retrievePastForProvider: function (providerId, callback) {
            var sql = 'SELECT * FROM Appointment WHERE ProviderId=$id AND Date < date(\'now\');';
            this.all(sql, {$id: providerId}, function (err, result) {
                var appointments = [];
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        appointments.push(SqliteAppointmentDAO.AppointmentFromDatabase(result[i]));
                    }
                }
                if (callback) {
                    callback(err, appointments);
                }
            });
        },

        retrievePastForContact: function (contactID, callback) {
            var sql = 'SELECT * FROM Appointment WHERE ContactId=$id AND Date < date(\'now\');';
            this.all(sql, {$id: contactID}, function (err, result) {
                var appointments = [];
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        appointments.push(SqliteAppointmentDAO.AppointmentFromDatabase(result[i]));
                    }
                }
                if (callback) {
                    callback(err, appointments);
                }
            });
        },
        retrieveFutureForProvider: function (providerId, callback) {
            var sql = 'SELECT * FROM Appointment WHERE ProviderId=$id AND Date >= date(\'now\');';
            this.all(sql, {$id: providerId}, function (err, result) {
                var appointments = [];
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        appointments.push(SqliteAppointmentDAO.AppointmentFromDatabase(result[i]));
                    }
                }
                if (callback) {
                    callback(err, appointments);
                }
            });
        },
        retrieveFutureForContact: function (contactID, callback) {
            var sql = 'SELECT * FROM Appointment WHERE ContactId=$id AND Date >= date(\'now\');';
            this.all(sql, {$id: contactID}, function (err, result) {
                var appointments = [];
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        appointments.push(SqliteAppointmentDAO.AppointmentFromDatabase(result[i]));
                    }
                }
                if (callback) {
                    callback(err, appointments);
                }
            });
        },

        retrieveDetailedAvailabilitiesForProvider: function (providerId, date, callback) {
            var sql =
                'SELECT timeSlot, a.ProviderId, Color FROM (' +
                    '    SELECT timeSlot, ProviderId, count(*) as occurances ' +
                    '    FROM ( ' +
                    '            SELECT time(Start) as timeSlot, ProviderId FROM Provider_Hours WHERE ProviderId = ? and Day = strftime(\'%w\',?) ' +
                    '        UNION ALL ' +
                    '            SELECT time(BreakStart) as timeSlot, ProviderId FROM Provider_Hours WHERE ProviderId = ? and Day = strftime(\'%w\',?) ' +
                    '        UNION ALL ' +
                    '            SELECT time(BreakEnd) as timeSlot , ProviderId FROM Provider_Hours WHERE ProviderId = ? and Day = strftime(\'%w\',?) ' +
                    '        UNION ALL ' +
                    '           SELECT time(End) as timeSlot, ProviderId FROM Provider_Hours WHERE ProviderId = ? and Day = strftime(\'%w\',?) ' +
                    '        UNION ALL ' +
                    '           SELECT time(Time) as timeSlot, ProviderId ' +
                    '           FROM Appointment as a ' +
                    '               LEFT JOIN Appointment_Type as b ON b.TypeId = a.TypeId ' +
                    '           WHERE ProviderId = ? AND Date = ? ' +
                    '       UNION ALL ' +
                    '           SELECT time(strftime(\'%s\',Time) + strftime(\'%s\',Duration), \'unixepoch\') as timeSlot, ProviderId ' +
                    '           FROM Appointment as a ' +
                    '               LEFT JOIN Appointment_Type as b ON b.TypeId = a.TypeId ' +
                    '           WHERE ProviderId = ? AND Date = ? ' +
                    '    )' +
                    '    GROUP BY timeSlot ' +
                    '    ORDER BY timeSlot ' +
                    ') AS a LEFT JOIN ON Provider.ProviderId = a.ProviderId ' +
                    'WHERE occurances = 1;';


            var database = this._db;
            database.all(sql,
                [
                    id, date, //servHrsStart
                    id, date, //servHrsBreakStart
                    id, date, //servHrsBreakEnd
                    id, date,  //servHrsEnd
                    id, date,  //appTime
                    id, date  //appEndTime
                ],

                function (err, result) {
                    if (callback) {
                        callback(err, result);
                    }
                });

        },
        remove: function (id, callback) {
            var sql = 'DELETE FROM Appointment WHERE AppointmentId=$id;';
            this.all(sql, {$id: id}, function (err) {
                if (callback) {
                    callback(err);
                }
            });
        },
        retrieveAllAvailabilities: function (start, end, callback) {
            var events = [];
            var total = 0;
            var oneDay = this._oneDay;
            var endDate = new Date(new Date(end).getTime() + oneDay);
            var iteration = 0;
            var dateRange = (new Date(end) - new Date(start)) / (oneDay);

            var expectedIterations = 0;
            var database = this._db;

            function getEventsRange() {
                database.serialize(function () {

                    database.each('SELECT ProviderId, Color FROM Provider;', function (err, row) {
                            database.serialize(function () {
                                var startDate = new Date(new Date(start).getTime() + oneDay);

                                var date = '';
                                while (startDate < endDate) {
                                    date = startDate.getFullYear() + '-' + ("0" + (startDate.getMonth() + 1)).slice(-2) + '-' + ("0" + startDate.getDate()).slice(-2);
                                    database.all(
                                        'SELECT Slot, ProviderId, ? as Date FROM (' +
                                            '    SELECT Slot, ProviderId,  count(*) as Occurrence' +
                                            '    FROM (' +
                                            '            SELECT time(Start) as Slot, ProviderId FROM Provider_Hours WHERE ProviderId = ? and Day = strftime(\'%w\',?)' +
                                            '        UNION ALL' +
                                            '            SELECT time(BreakStart) as Slot, ProviderId FROM Provider_Hours WHERE ProviderId = ? and Day = strftime(\'%w\',?)' +
                                            '        UNION ALL' +
                                            '            SELECT time(BreakEnd) as Slot , ProviderId FROM Provider_Hours WHERE ProviderId = ? and Day = strftime(\'%w\',?)' +
                                            '        UNION ALL' +
                                            '           SELECT time(End) as Slot, ProviderId FROM Provider_Hours WHERE ProviderId = ? and Day = strftime(\'%w\',?)' +
                                            '        UNION ALL' +
                                            '           SELECT time(Time) as Slot, ProviderId' +
                                            '           FROM Appointment as a' +
                                            '               LEFT JOIN Appointment_Type as b ON b.TypeId = a.TypeId' +
                                            '           WHERE ProviderId = ? AND Date = ?' +
                                            '       UNION ALL' +
                                            '           SELECT time(strftime(\'%s\',Time) + strftime(\'%s\',Duration), \'unixepoch\') as Slot, ProviderId' +
                                            '           FROM Appointment as a' +
                                            '               LEFT JOIN Appointment_Type as b ON b.TypeId = a.TypeId' +
                                            '           WHERE ProviderId = ? AND Date = ?' +
                                            '    )' +
                                            '    GROUP BY Slot' +
                                            '    ORDER BY Slot' +
                                            ')' +
                                            'WHERE Occurrence = 1;',
                                        [
                                            date,
                                            row.ProviderId, date, //Start
                                            row.ProviderId, date, //BreakStart
                                            row.ProviderId, date, //BreakEnd
                                            row.ProviderId, date,  //End
                                            row.ProviderId, date,  //Time
                                            row.ProviderId, date  //appEndTime
                                        ]
                                        , function (err, result) {
                                            if (err && callback) {
                                                callback(true, events);
                                            } else {
                                                for (var j = 0; j < result.length; j += 2) {
                                                    events = events.concat(SqliteAppointmentDAO.AvailabilityFromDatabase(result[j], result[j + 1], row.Color));
                                                }
                                                iteration++;
                                                if (iteration == expectedIterations && callback) {
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
                            if (expectedIterations == 0 && callback) {
                                callback(err, events);
                            }
                        }
                    );
                });
            }

            getEventsRange();
        }


    })
    ;

SqliteAppointmentDAO.AppointmentFromDatabase = function (row) {
    var appointment = new Appointment();

    appointment.setId(row.AppointmentId);
    appointment.setTypeId(row.TypeId);
    appointment.setContactId(row.ContactId);
    appointment.setProviderId(row.ProviderId);
    appointment.setDate(row.Date);
    appointment.setTime(row.Time);

    return appointment;
};

SqliteAppointmentDAO.AvailabilityFromDatabase = function (row, endRow, color) {
    var availability = new Availability();
    availability.setId(row.ProviderId);
    availability.setColor(color);
    availability.setStart(row.Slot);
    availability.setEnd(endRow.Slot);
    availability.setDate(row.Date);

    return availability;
}

module.exports = SqliteAppointmentDAO;