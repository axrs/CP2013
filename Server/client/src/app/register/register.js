angular.module('ngBoilerplate.register', [
        'ui.router',
        'placeholders',
        'ui.bootstrap',
        'RESTService',
        'LocalService',
        'AuthService',
        'ngGrid', // angular grid
        'ui', // angular ui
        'ui.bootstrap' // jquery ui bootstrap
    ])

    .config(function config($stateProvider) {

        var access = routingConfig.accessLevels;

        $stateProvider.state('register', {
            access: access.public,
            url: '/register',
            views: {
                "main": {
                    controller: 'RegisterCtrl',
                    templateUrl: 'register/register.index.tpl.html'
                }
            },
            data: { pageTitle: 'Register' }
        });
    })
    .controller('RegisterCtrl', function RegisterCtrl($scope,$location, AuthService, RESTService) {
        if (AuthService.isLoggedIn()){
            $location.path('/profile');
        }
        $scope.user = {};
        $scope.errors = {};
        $scope.alerts = [];

        function errorMessage(error, message) {
            $scope.errors[error] = message;
        }

        function clearError(error) {
            $scope.errors[error] = null;
        }


        //Check username with server.
        var checkUsernameTimer = null;
        var checkUsernameTimeout = 1000;
        $scope.$watch('user.username', function () {
            if (checkUsernameTimeout || $scope.user.username !== "") {
                clearTimeout(checkUsernameTimer);
            }
            if ($scope.user.username) {
                checkUsernameTimer = setTimeout(checkUsernameWithServer, checkUsernameTimeout);
            }
        }, true);

        function checkUsernameWithServer() {
            clearTimeout(checkUsernameTimer);
            RESTService.get('/api/user/' + $scope.user.username).
                success(function (data, status) {
                    if (status == 200) {
                        clearError("usernameError");
                    }
                }).error(function (data, status) {
                    errorMessage("usernameError", "Username is already taken.");
                });
        }

        $scope.$watch('user.password', function () {
            if ($scope.user.password !== $scope.user.validationPassword) {
                errorMessage("passwordError", "Selected passwords do not match.");
            }
        }, true);

        $scope.$watch('user.validationPassword', function () {
            if ($scope.user.password === $scope.user.validationPassword) {
                clearError("passwordError");
            } else if ($scope.user.password !== $scope.user.validationPassword) {
                errorMessage("passwordError", "Selected passwords do not match.");
            }
        }, true);

        $scope.tryRegistration = function () {
            if (!validateUser()) {
                return;
            }
            RESTService.put('/api/users', $scope.user).
                success(function (data, status, headers, config) {
                    if (status == 201) {
                        $scope.user = data;
                        AuthService.changeUser($scope.user);
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $scope.alerts.push({type: 'error', title: 'Form Error:', msg: "A user must have a Name, Surname, Username and Password."});
                            break;
                        case 409:
                            $scope.alerts.push({type: 'error', title: 'Conflict:', msg: "Specified username is already taken."});
                            break;
                        case 500:
                            $scope.alerts.push({type: 'error', title: 'Database Error:', msg: "Error creating the user."});
                            break;
                    }
                });

        };

        function isDefinedAndSet(value) {
            return (value && value !== "");
        }

        function validateUser() {

            var valid = isDefinedAndSet($scope.user.name);
            valid = valid && isDefinedAndSet($scope.user.surname);
            valid = valid && isDefinedAndSet($scope.user.userName);
            valid = valid && isDefinedAndSet($scope.user.password);
            valid = valid && isDefinedAndSet($scope.user.validationPassword);
            valid = valid && ($scope.user.password === $scope.user.validationPassword);

            return valid;
        }

    })
;

