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
                    templateUrl: 'profile/index.tpl.html'
                }
            },
            data: { pageTitle: 'About You!' }
        });
    })


    .controller('ProfileCtrl', function ProfileCtrl($scope, AuthService) {
        $scope.user = AuthService.user;
    })

;

