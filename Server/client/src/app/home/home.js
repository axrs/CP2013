/**
 * Each section of the site has its own module. It probably also has
 * submodules, though this boilerplate is too simple to demonstrate it. Within
 * `src/app/home`, however, could exist several additional folders representing
 * additional modules that would then be listed as dependencies of this one.
 * For example, a `note` section could have the submodules `note.create`,
 * `note.delete`, `note.edit`, etc.
 *
 * Regardless, so long as dependencies are managed correctly, the build process
 * will automatically take take of the rest.
 *
 * The dependencies block here is also where component dependencies should be
 * specified, as shown below.
 */
angular.module('ngBoilerplate.home', [
        'ui.router',
        'placeholders',
        'ui.bootstrap',
        'RESTService',
        'LocalService',
        'AuthService',
        'ngGrid', // angular grid
        'ui', // angular ui
        'ui.calendar'
    ])

/**
 * Each section or module of the site can also have its own routes. AngularJS
 * will handle ensuring they are all available at run-time, but splitting it
 * this way makes each module more "self-contained".
 */
    .config(function config($stateProvider) {
        var access = routingConfig.accessLevels;

        $stateProvider.state('home', {
            access: access.public,
            url: '/',
            views: {
                "main": {
                    controller: 'HomeCtrl',
                    templateUrl: 'home/home.tpl.html'
                }
            },
            data: { pageTitle: 'Home' }
        });
    })

/**
 * And of course we define a controller for our route.
 */
    .controller('HomeCtrl', function HomeController($rootScope, $scope, RESTService, AuthService) {


        /* event source that contains custom events on the scope */
        $scope.events = [ ];

        $scope.provider = {};
        $scope.providers = [{
            providerId: 0,
            name: "All Providers"
        }];

        $scope.allowedTime = [];

        $scope.showOnlyProviderId = 0;
        $scope.providerView = {};

        $scope.selectedTime = "";
        $scope.selectedTimeSlot = {};
        $scope.proposedAppointment = {};


        $scope.selectedAppointment = {};
        $scope.appointmentTypes = [];

        $scope.user = AuthService.user;

        $scope.contact = AuthService.user;

        $scope.validBooking = false;


        $scope.$watch('providerView', function () {

            if ($scope.providerView.providerId > 0) {
                $scope.showOnlyProviderId = $scope.providerView.providerId;
            } else {
                $scope.providerView.providerId = 0;
            }
            $scope.clear();
            $scope.myCalendar.fullCalendar('refetchEvents');

        }, true);

        function initialiseObjects() {
            RESTService.get('/api/providers').
                success(function (data, status, headers, config) {
                    if (status == 200) {
                        $scope.providers = $scope.providers.concat(data);
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 500:
                            $rootScope.addError('Database Error:', "Error getting providers.");
                            break;
                    }
                });

            RESTService.get('/api/types').
                success(function (data, status, headers, config) {
                    if (status == 200) {
                        $scope.appointmentTypes = data;
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 500:
                            $rootScope.addError('Database Error:', "Error getting appointment types.");
                            break;
                    }
                });
        }

        initialiseObjects();


        $scope.$watch('selectedAppointment', function () {
            if ($scope.selectedTimeSlot.providerId) {
                adjustBookingTimes($scope.selectedTimeSlot.timeStart, $scope.selectedTimeSlot.timeEnd, $scope.selectedAppointment.duration);
            }

        }, true);
        $scope.$watch('selectedTime', function () {

            if ($scope.selectedTime !== "") {
                $scope.proposedAppointment.providerId = $scope.provider.providerId;
                $scope.proposedAppointment.contactId = $scope.contact.contactId;
                $scope.proposedAppointment.typeId = $scope.selectedAppointment.typeId;
                $scope.proposedAppointment.time = $scope.selectedTime.time;
                $scope.proposedAppointment.date = $scope.selectedTimeSlot.dateString;
                $scope.validBooking = true;
            }

        }, true);

        $scope.$watch('user', function () {
            $scope.clear();
            $scope.myCalendar.fullCalendar('refetchEvents');

        }, true);

        $scope.tryCancelBooking = function () {
            if ($scope.selectedTimeSlot.appointmentId > 0) {
                RESTService.remove('/api/appointments/' + $scope.selectedTimeSlot.appointmentId).
                    success(function (data, status, headers, config) {
                        $scope.clear();
                        $scope.myCalendar.fullCalendar('refetchEvents');
                    }).
                    error(function (data, status, headers, config) {
                        switch (status) {
                            case 501:
                                $rootScope.addError('Deletion Error:', 'Method not Implemented.');
                        }
                    });
            }
        };


        $scope.tryMakeBooking = function () {
            if (!$scope.validBooking) {
                return;
            }
            RESTService.put('/api/appointments', $scope.proposedAppointment).
                success(function (data, status, headers, config) {
                    if (status == 201) {
                        $scope.clear();
                        $scope.myCalendar.fullCalendar('refetchEvents');
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $scope.alerts.push({type: 'error', title: 'Form Error:', msg: "Invalid appointment options."});
                            break;
                        case 500:
                            $scope.alerts.push({type: 'error', title: 'Database Error:', msg: "Error creating the appointment."});
                            break;
                    }
                });

        };

        $scope.clear = function () {
            $scope.selectedAppointment = {};
            $scope.provider = {};
            $scope.validBooking = false;
            $scope.proposedAppointment = {};
            $scope.allowedTime = [];
            $scope.selectedTimeSlot = {};
            $scope.selectedTime = "";
        };

        function getMinutes(value) {
            return parseInt(Math.floor(value.split(':')[0] * 60) + parseInt(value.split(':')[1], 10), 10);
        }

        function adjustBookingTimes(start, end, duration) {
            $scope.allowedTime = [];

            var durationMinutes = getMinutes(duration);
            var startMinutes = getMinutes(start);
            var endMinutes = getMinutes(end);

            endMinutes = endMinutes - durationMinutes;

            var totalDuration = endMinutes - startMinutes;

            var timeStamp = "";
            var step = 0;
            var nextTime = 0;
            for (var i = 0; step < totalDuration; i++) {
                step = i * 15;
                nextTime = startMinutes + step;

                var hours = Math.floor(nextTime / 60);
                var minutes = parseInt(nextTime % 60, 10);
                timeStamp = (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes);

                $scope.allowedTime.push({time: timeStamp});
            }
            console.log($scope.allowedTime);
        }


        /* event source that calls a function on every view switch */
        $scope.bookedAppointments = function (start, end, callback) {

            $scope.clearEvents();
            RESTService.get('/api/appointments').
                success(function (data, status, headers, config) {
                    if (status == 200) {
                        var appointments = [];
                        for (var i = 0; i < data.length; i++) {
                            var timeSlot = data[i];
                            timeSlot.timeStart = timeSlot.time;
                            timeSlot.timeEnd = timeSlot.endTime;
                            timeSlot.dateString = timeSlot.date;

                            timeSlot.start = timeSlot.date + " " + timeSlot.time;
                            timeSlot.end = timeSlot.date + " " + timeSlot.endTime;

                            timeSlot.title = timeSlot.description;
                            timeSlot.allDay = false;
                            if ($scope.showOnlyProviderId === 0 || $scope.showOnlyProviderId == timeSlot.providerId) {
                                appointments.push(timeSlot);
                            }
                        }
                        console.log(appointments);
                        callback(appointments);
                    }
                }).
                error(function (data, status, headers, config) {
                    callback([]);
                });
        };


        /* event source that calls a function on every view switch */
        $scope.appointmentAvailabilities = function (start, end, callback) {

            if (start < new Date()) {
                start = new Date();
            }

            var startString = start.getFullYear() + '-' + ("0" + (start.getMonth() + 1)).slice(-2) + '-' + ("0" + start.getDate()).slice(-2);
            var endString = end.getFullYear() + '-' + ("0" + (end.getMonth() + 1)).slice(-2) + '-' + ("0" + end.getDate()).slice(-2);


            $scope.clearEvents();
            RESTService.get('/api/available/' + startString + "/" + endString).
                success(function (data, status, headers, config) {
                    if (status == 200) {
                        var availabilities = [];
                        for (var i = 0; i < data.length; i++) {
                            var timeSlot = data[i];
                            timeSlot.timeStart = timeSlot.start;
                            timeSlot.timeEnd = timeSlot.end;
                            timeSlot.dateString = timeSlot.date;

                            timeSlot.start = timeSlot.date + " " + timeSlot.start;
                            timeSlot.end = timeSlot.date + " " + timeSlot.end;

                            timeSlot.title = 'Available';
                            timeSlot.allDay = false;

                            if ($scope.showOnlyProviderId === 0 || $scope.showOnlyProviderId == timeSlot.providerId) {
                                availabilities.push(timeSlot);
                            }
                        }
                        callback(availabilities);
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $rootScope.addError('Form Error:', "A contact must have a First and Last name.");
                            break;
                        case 409:
                            $rootScope.addError('Conflict:', "A contact with the specified name exists.");
                            break;
                        case 500:
                            $rootScope.addError('Database Error:', "Error creating the contact.");
                            break;
                    }
                    callback([]);
                });
        };
        /* alert on eventClick */
        $scope.alertEventOnClick = function (date, allDay, jsEvent, view) {
            $scope.$apply(function () {
                for (var i = 0; i < $scope.providers.length; i++) {
                    var p = $scope.providers[i];
                    if (p.providerId === date.providerId) {
                        $scope.provider = p;
                        $scope.selectedTimeSlot = date;
                        break;
                    }
                }
            });
        };

        /* config object */
        $scope.uiConfig = {
            calendar: {
                height: 600,
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
                viewRender: function (view, element) {
                    var minDate = new Date();
                    if (view.start <= minDate) {
                        $('#calendar .fc-button-prev').addClass('fc-state-disabled');
                    } else {
                        $('#calendar .fc-button-prev').removeClass('fc-state-disabled');
                    }
                },
                eventClick: $scope.alertEventOnClick
            }
        };
        /* event sources array*/
        $scope.eventSources = [$scope.events, $scope.appointmentAvailabilities, $scope.bookedAppointments];

        $scope.clearEvents = function () {
            $scope.events = [];
        };
    })

;

