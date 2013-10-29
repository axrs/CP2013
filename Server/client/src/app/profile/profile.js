angular.module('ngBoilerplate.profile', [
        'ui.router'
    ])


    .config(function config($stateProvider) {
        var access = routingConfig.accessLevels;

        $stateProvider.state('profile', {
            access: access.user,
            url: '/profile',
            views: {
                "main": {
                    controller: 'ProfileCtrl',
                    templateUrl: 'profile/profile.index.tpl.html'
                }
            },
            data: { pageTitle: 'About You!' }
        });
    })


    .controller('ProfileCtrl', function ProfileCtrl($scope, AuthService, RESTService) {
        $scope.user = AuthService.user;

        console.log($scope.user);
        $scope.tryUpdate = function () {
            if (!validateUser()) {
                return;
            }
            RESTService.put('/api/user/' + $scope.user.userId, $scope.user).
                success(function (data, status, headers, config) {
                    if (status == 201) {
                        $scope.user = data;
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $scope.alerts.push({type: 'error', title: 'Form Error:', msg: "Your profile must have a Name & Surname"});
                            break;
                        case 409:
                            $scope.alerts.push({type: 'error', title: 'Conflict:', msg: "Conflict Error."});
                            break;
                        case 500:
                            $scope.alerts.push({type: 'error', title: 'Database Error:', msg: "Error updating your profile."});
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
            return valid;
        }
    })

;

