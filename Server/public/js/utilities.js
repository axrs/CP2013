Element.prototype.remove = function () {
    this.parentElement.removeChild(this);
}
NodeList.prototype.remove = HTMLCollection.prototype.remove = function () {
    for (var i = 0, len = this.length; i < len; i++) {
        if (this[i] && this[i].parentElement) {
            this[i].parentElement.removeChild(this[i]);
        }
    }
}

var lastEvent = null;
$(document).ready(function () {
    $('.datepicker').datepicker({
        format: 'yyyy-mm-dd'
    });

    function loadAppointmentTypes() {
        var selectControl = $('#appointmentTypeList')[0];
        if (selectControl) {
            while (selectControl.firstChild) {
                selectControl.removeChild(selectControl.firstChild);
            }
            $.getJSON('/api/appointments/types', function (data) {
                $.each(data, function (val, type) {
                    console.log(type);
                    $('#appointmentTypeList').append($('<option>', {
                        value: type.appTypeId,
                        text: type.appTypeDescription
                    }));
                });
            });
        }
    }

    function setStaffMember(staffId) {
        $.getJSON('/api/staff/' + staffId, function (data) {
            if ($('#staffMemberName').length == 1) {
                $('#staffMemberName')[0].setAttribute('value', sprintf('%s %s', data.contForename, data.contSurname));
                $('#staffMemberId')[0].setAttribute('value', staffId);
            }
            if ($('#eventDescription #appointmentStaff').length == 1) {
                $('#eventDescription #appointmentStaff')[0].innerHTML =
                    sprintf('<a href="/staff/%s">%s %s</a>', data.servId, data.contForename, data.contSurname);
            }
        });
    }

    function setContact(contId) {
        $.getJSON('/api/contacts/' + contId, function (data) {
            if ($('#eventDescription #appointmentContact').length == 1) {
                $('#eventDescription #appointmentContact')[0].innerHTML =
                    sprintf('<a href="/contacts/%s">%s %s</a>', data.contId, data.contForename, data.contSurname);
            }
        });
    }

    function setAppointmentDate(start, end) {
        if ($('#bookingDate').length == 1) {
            var date = new Date(start);
            $('#bookingDate')[0].value = date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDate()).slice(-2);
            $('#bookingDateValue')[0].value = date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDate()).slice(-2);
        }
        if ($('#eventDescription #appointmentDate').length == 1) {
            $('#eventDescription #appointmentDate')[0].innerHTML =
                sprintf('%s', moment(start).format("dddd, MMMM Do YYYY, h:mm:ss a"));
        }
    }

    function setAppointmentType(appTypeId) {
        $.getJSON('/api/appointments/types/' + appTypeId, function (data) {

            if ($('#eventDescription #appointmentType').length == 1) {
                $('#eventDescription #appointmentType')[0].innerHTML =
                    sprintf('%s', data.appTypeDescription);
            }
        });
    }


    function loadContacts() {
        var selectControl = $('#contactControlList')[0];
        if (selectControl) {
            while (selectControl.firstChild) {
                selectControl.removeChild(selectControl.firstChild);
            }
            $.getJSON('/api/contacts', function (data) {
                $.each(data, function (val, contact) {
                    $('#contactControlList').append($('<option>', {
                        value: contact.contId,
                        text: contact.contForename + ' ' + contact.contSurname
                    }));
                });
            });
        }
    }

    function loadAppointmentTimes(start, end) {
        if (start && $('#appointmentTime').length == 1) {
            $('#appointmentTime').timepicker('destroy');
            $('#appointmentTime')[0].value = "";
            $('#appointmentTime').removeClass('hidden');
            var selectControl = $('#appointmentTypeList')[0];
            var appTypeId = selectControl.options[selectControl.selectedIndex].value;

            if (appTypeId > 0) {
                $.getJSON('/api/appointments/types/' + appTypeId, function (data) {
                    var duration = data.appTypeDuration.split(':');
                    var step = (+duration[0]) * 60 + (+duration[1]);

                    $('#appointmentTime').timepicker({
                        step: 15,
                        at_row: 4,
                        active_from: start.getHours() + ":" + start.getMinutes(),
                        active_to: (end.getHours() - duration[0]) + ":" + (end.getMinutes() - duration[1])
                    });

                });
            }
        } else {
            $('#appointmentTime').timepicker('destroy');
            $('#appointmentTime').addClass('hidden');
        }
    }

    if ($('#appointmentTypeList').length == 1) {
        $('#appointmentTypeList')[0].onchange = function () {
            loadAppointmentTimes(lastEvent)
        }
    }

    if ($('#calendar').length > 0) {
        loadAppointmentTypes();
        loadContacts();
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        $('#calendar').fullCalendar({
            defaultView: 'agendaWeek',
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'agendaWeek,agendaDay'
            },
            editable: false,
            columnFormat: {
                month: 'ddd',
                week: 'ddd d',
                day: ''
            },
            events: function (start, end, callback) {
                if (start < new Date())
                    start = new Date();

                var startString = start.getFullYear() + '-' + ("0" + (start.getMonth() + 1)).slice(-2) + '-' + ("0" + start.getDate()).slice(-2);
                var endString = end.getFullYear() + '-' + ("0" + (end.getMonth() + 1)).slice(-2) + '-' + ("0" + end.getDate()).slice(-2);
                $.ajax({
                    url: '/api/staff/available/' + startString + '/' + endString,
                    success: function (doc) {
                        var events = doc;
                        callback(events);
                    }
                });
            },

            viewRender: function (view, element) {
                var minDate = new Date();
                if (view.start <= minDate) {
                    $('#calendar .fc-button-prev').addClass('fc-state-disabled');
                } else {
                    $('#calendar .fc-button-prev').removeClass('fc-state-disabled')
                }
            },
            eventClick: function (calEvent, jsEvent, view) {
                lastEvent = calEvent;
                setStaffMember(calEvent.servId);
                setAppointmentDate(calEvent.start, calEvent.end);
                loadAppointmentTimes(calEvent.start, calEvent.end);
            }
        });
    }


    if ($('#appCalendar').length > 0) {
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        $('#appCalendar').fullCalendar({
            defaultView: 'agendaWeek',
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'agendaWeek,agendaDay'
            },
            editable: false,
            columnFormat: {
                month: 'ddd',
                week: 'ddd d',
                day: ''
            },
            events: function (start, end, callback) {
                var startString = start.getFullYear() + '-' + ("0" + (start.getMonth() + 1)).slice(-2) + '-' + ("0" + start.getDate()).slice(-2);
                var endString = end.getFullYear() + '-' + ("0" + (end.getMonth() + 1)).slice(-2) + '-' + ("0" + end.getDate()).slice(-2);

                $.ajax({
                    url: '/api/staff/appointments/' + startString + '/' + endString,
                    success: function (doc) {
                        var events = doc;
                        callback(events);
                    }
                });
            },
            eventClick: function (calEvent, jsEvent, view) {

                if ($('#eventDescription #eventDescriptionHelp').length == 1) {
                    $('#eventDescription #eventDescriptionHelp').alert('close');
                }

                lastEvent = calEvent;
                setStaffMember(calEvent.servId);
                setContact(calEvent.contId);
                setAppointmentDate(calEvent.start, calEvent.end);
                setAppointmentType(calEvent.appTypeId);
            }
        });
    }
});