module.exports = {
    getAppointmentAvailabilitiesById: function (Model) {
        return function (req, res, next) {

            Model.getProviderAvailabilities(req.params.id, req.params.date, function (err, results) {
                var model = {};
                model.servId = parseInt(req.params.id);
                model.date = req.params.date;
                model.timeSlots = [];
                for (var i = 0; i < results.length; i += 2) {
                    var slot = {};
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
                        var slot = {};
                        slot.title = 'Available';
                        slot.servId = results[i].servId;
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
    availabilitiesRangeById: function(Model){
        return function (req, res, next) {

            Model.getProviderAvailabilitiesRange(req.params.id, req.params.start,req.params.end, function (err, results) {

                if (results) {
                    var model = [];

                    for (var i = 0; i < results.length; i += 2) {
                        var slot = {};
                        slot.title = '';
                        slot.servId = results[i].servId;
                        slot.color = results[i].servColor;
                        slot.start = results[i].date + ' ' + results[i].timeSlot;
                        slot.end = results[i+1].date + ' ' + results[i + 1].timeSlot;
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
    availabilitiesRange: function(Model){
        return function (req, res, next) {

            Model.getAllProviderAvailabilitiesRange(req.params.start,req.params.end, function (err, results) {

                if (results) {
                    var model = [];
                    for (var i = 0; i < results.length; i += 2) {
                        var slot = {};
                        slot.title = '';
                        slot.servId = results[i].servId;
                        slot.color = results[i].servColor;

                        slot.start = results[i].date + ' ' + results[i].timeSlot;
                        slot.end = results[i+1].date + ' ' + results[i + 1].timeSlot;
                        slot.allDay = false;
                        model = model.concat(slot);
                    }
                    req.model = model;
                    console.log(model);
                    next();
                } else {
                    res.statusCodes.apiStatus500(req, res);
                }
            });
        }
    }
}
