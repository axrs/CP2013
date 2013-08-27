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

    function loadStaffMember(calEvent) {
        if ($('#staffMemberName').length == 1) {
            $.getJSON('/api/staff/' + calEvent.servId, function (data) {
                $('#staffMemberName')[0].setAttribute('value', data.contSurname + ', ' + data.contForename);
                $('#staffMemberId')[0].setAttribute('value', calEvent.servId);
            });
        }
    }

    function setAppointmentDate(calEvent) {
        if ($('#appointmentDate').length == 1) {
            var date = new Date(calEvent.start);
            $('#appointmentDate')[0].value = date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDate()).slice(-2);
            $('#appointmentDateValue')[0].value = date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDate()).slice(-2);
        }
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

    function loadAppointmentTimes(calEvent) {
        if (calEvent && $('#appointmentTime').length == 1) {
            $('#appointmentTime').timepicker('destroy');
            $('#appointmentTime')[0].value = "";
            $('#appointmentTime').removeClass('hidden');
            var selectControl = $('#appointmentTypeList')[0];
            var appTypeId = selectControl.options[selectControl.selectedIndex].value;

            var appTypeDuration = 0;

            if (appTypeId > 0) {
                $.getJSON('/api/appointments/types/' + appTypeId, function (data) {
                    var duration = data.appTypeDuration.split(':');
                    var step = (+duration[0]) * 60 + (+duration[1]);

                    $('#appointmentTime').timepicker({
                        step: 15,
                        at_row: 4,
                        active_from: calEvent.start.getHours() + ":" + calEvent.start.getMinutes(),
                        active_to: (calEvent.end.getHours() - duration[0]) + ":" + (calEvent.end.getMinutes() - duration[1])
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
                loadStaffMember(calEvent);
                setAppointmentDate(calEvent);
                loadAppointmentTimes(calEvent);
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

            }
        });
    }




});