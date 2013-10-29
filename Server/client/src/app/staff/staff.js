angular.module('ngBoilerplate.staff', [
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

        $stateProvider.state('staff', {
            access: access.public,
            url: '/staff',
            views: {
                "main": {
                    controller: 'StaffCtrl',
                    templateUrl: 'staff/staff.index.tpl.html'
                }
            },
            data: { pageTitle: 'Staff' }
        });
    })
    .controller('StaffCtrl', function StaffCtrl($rootScope, $scope, RESTService, AuthService) {
        $scope.providers = [];


        RESTService.get('/api/providers').
            success(function (data, status, headers, config) {
                if (status == 200) {
                    $scope.providers = data;
                }
            });

    });

