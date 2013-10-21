angular.module('ngBoilerplate', [
        'templates-app',
        'templates-common',
        'ui.router',
        'RESTService',
        'LocalService',
        'AuthService',
        'ngBoilerplate.home',
        'ngBoilerplate.about',
        'ngBoilerplate.contacts',
        'ui.router'
    ])

    .config(function myAppConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');
    })

    .run(function run($rootScope, AuthService, RESTService, LocalService) {
        /*
         $rootScope.restService = RESTService;
         $rootScope.LocalService = LocalService;
         $rootScope.authService = AuthService;
         */
    })

    .controller('AppCtrl', function AppCtrl($scope, $location) {
        $scope.copyright = 'CP2013 | Alexander Scott, Melinda Kingsun, Timothy Hart';
        $scope.siteName = 'Shear-N-Dipity';

        $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle + ' | ' + $scope.siteName;
            }
        });
    })

;

