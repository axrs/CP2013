module.exports = {
    getAppointmentAvailabilitiesById: function (Model) {
        return function (req, res, next) {

            Model.getProviderAvailabilities(req.params.id, req.params.date, function (err, results) {
                var model = {};
                model.servId = parseInt(req.params.id);
                model.date = req.params.date;
                model.timeSlots = [];
                for (var i = 0; i < results.length; i += 2) {
                    var slot = results[i];
                    slot.title = 'Available';
                    slot.start = req.params.date + ' ' + results[i].timeSlot;
                    slot.startTime = results[i].timeSlot;
                    slot.end = req.params.date + ' ' + results[i + 1].timeSlot;
                    slot.endTime = results[i + 1].timeSlot;
                    model.timeSlots = model.timeSlots.concat(slot);
                }
            });
            next();
        }
    },
    availabilitiesById: function (Model) {
        return function (req, res, next) {

            Model.getProviderAvailabilities(req.params.id, req.params.date, function (err, results) {

                if (results) {
                    var model = [];

                    for (var i = 0; i < results.length; i += 2) {
                        var slot = results[i];
                        slot.title = 'Available';
                        slot.color = results[i].servColor;
                        slot.start = req.params.date + ' ' + results[i].timeSlot;
                        slot.end = req.params.date + ' ' + results[i + 1].timeSlot;
                        slot.allDay = false;
                        model = model.concat(slot);
                    }
                    req.model = model;
                    next();
                } else {
                    res.statusCodes.apiStatus500(req, res);
                }
            });
        }
    },
    availabilitiesRangeById: function (Model) {
        return function (req, res, next) {

            Model.getProviderAvailabilitiesRange(req.params.id, req.params.start, req.params.end, function (err, results) {

                if (results) {
                    var model = [];

                    for (var i = 0; i < results.length; i += 2) {
                        var slot = results[i];
                        slot.title = '';
                        slot.color = results[i].servColor;
                        slot.start = results[i].date + ' ' + results[i].timeSlot;
                        slot.end = results[i + 1].date + ' ' + results[i + 1].timeSlot;
                        slot.allDay = false;
                        model = model.concat(slot);
                    }
                    req.model = model;
                    next();
                } else {
                    res.statusCodes.apiStatus500(req, res);
                }
            });
        }
    },
    availabilitiesRange: function (Model) {
        return function (req, res, next) {

            Model.getAllProviderAvailabilitiesRange(req.params.start, req.params.end, function (err, results) {

                if (results) {
                    var model = [];
                    for (var i = 0; i < results.length; i += 2) {
                        var slot = results[i];
                        slot.title = '';
                        slot.color = results[i].servColor;
                        slot.start = results[i].date + ' ' + results[i].timeSlot;
                        slot.end = results[i + 1].date + ' ' + results[i + 1].timeSlot;
                        slot.allDay = false;
                        model = model.concat(slot);
                    }
                    req.model = model;
                    next();
                } else {
                    res.statusCodes.apiStatus500(req, res);
                }
            });
        }
    },
    appointmentsRange: function (Model) {
        return function (req, res, next) {

            Model.getAllProviderAppointmentsRange(req.params.start, req.params.end, function (err, results) {
                if (results) {
                    var model = [];
                    for (var i = 0; i < results.length; i++) {
                        var slot = results[i];
                        slot.title = results[i].appTypeDescription + ' for ' + results[i].contForename + ' ' + results[i].contSurname;
                        slot.color = results[i].servColor;
                        slot.staff = results[i].servForename + ' ' + results[i].servSurname;
                        slot.start = results[i].appDate + ' ' + results[i].appTime;
                        slot.end = results[i].appDate + ' ' + results[i].appEndTime;
                        slot.allDay = false;
                        model = model.concat(slot);
                    }
                    req.model = model;
                    next();
                } else {
                    res.statusCodes.apiStatus500(req, res);
                }
            });
        }
    }
}
