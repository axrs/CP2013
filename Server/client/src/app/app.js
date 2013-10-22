angular.module('ngBoilerplate', [
        'templates-app',
        'templates-common',
        'ui.router',
        'RESTService',
        'LocalService',
        'AuthService',
        'ngCookies',
        'ngBoilerplate.home',
        'ngBoilerplate.about',
        'ngBoilerplate.contacts',
        'ui.router'
    ])

    .config(function myAppConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');
        var access = routingConfig.accessLevels;
    })

    .run(function run($rootScope, AuthService, RESTService, LocalService, $location) {

        $rootScope.user = AuthService.user;
        $rootScope.userRoles = AuthService.userRoles;
        $rootScope.accessLevels = AuthService.accessLevels;

        $rootScope.restService = RESTService;
        $rootScope.LocalService = LocalService;
        $rootScope.authService = AuthService;

        /**
         * Watch for location change requests and prevent unauthorised access
         */
        $rootScope.$on("$stateChangeStart", function (event, next, current) {
            $rootScope.error = null;

            if (!AuthService.authorize(next.access, AuthService.user.role)) {
                event.preventDefault();
                if (AuthService.isLoggedIn()) {
                    $location.path('/home');
                }
                else {
                    $location.path('/about');
                }
            }
        });
    })

    .controller('AppCtrl', function AppCtrl($scope, $location) {


        $scope.copyright = 'CP2013 | Alexander Scott, Melinda Kingsun, Timothy Hart';
        $scope.siteName = 'Shear-N-Dipity';

        /**
         * Watch for scope change events, and adjust general data accordingly.
         */
        $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle + ' | ' + $scope.siteName;
            }
        });
    })

;

