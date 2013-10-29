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
        'ngBoilerplate.register',
        'ngBoilerplate.types',
        'ngBoilerplate.profile',
        'ngBoilerplate.404',
        'ngBoilerplate.401'
    ])

    .config(function myAppConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/404');
    })

    .run(function run($rootScope, AuthService, RESTService, LocalService, $location) {

        $rootScope.user = AuthService.user;
        $rootScope.userRoles = AuthService.userRoles;
        $rootScope.accessLevels = AuthService.accessLevels;

        $rootScope.restService = RESTService;
        $rootScope.LocalService = LocalService;
        $rootScope.authService = AuthService;


        $rootScope.alerts = [];

        $rootScope.closeAlert = function (index) {
            $rootScope.alerts.splice(index, 1);
        };

        $rootScope.addAlert = function (messageType, messageTitle, messageBody) {
            $rootScope.alerts.push({type: messageType, title: messageTitle, msg: messageBody});
        };

        $rootScope.addError = function (messageTitle, messageBody) {
            $rootScope.alerts.push({type: 'error', title: messageTitle, msg: messageBody});
        };
        $rootScope.addInfo = function (messageTitle, messageBody) {
            $rootScope.alerts.push({type: 'info', title: messageTitle, msg: messageBody});
        };
        $rootScope.addSuccess = function (messageTitle, messageBody) {
            $rootScope.alerts.push({type: 'success', title: messageTitle, msg: messageBody});
        };

        $rootScope.navBarLogin = {};
        $rootScope.clearLoginForm = function () {
            $rootScope.navBarLogin.userName = "";
            $rootScope.navBarLogin.password = "";
        };
        /**
         * Watch for location change requests and prevent unauthorised access
         */
        $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
            if (!AuthService.authorize(toState.access)) {
                event.preventDefault();
                if (AuthService.isLoggedIn()) {
                    $location.path('/');
                }
                else {
                    $location.path('/401');
                }
            }
        });
    })

    .controller('AppCtrl', function AppCtrl($rootScope,$scope, $location) {

        $scope.copyright = 'CP2013 | Alexander Scott, Melinda Kingsun, Timothy Hart';
        $scope.siteName = 'Shear-N-Dipity';

        /**
         * Watch for scope change events, and adjust general data accordingly.
         */
        $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle + ' | ' + $scope.siteName;
                $rootScope.alerts = [];
            }
        });
    })

;

