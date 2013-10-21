'use strict';

// declare top-level module which depends on filters,and services
var myApp = angular.module('myApp',
    ['myApp.filters',
        'oauthCallback',
        'configuration',
        'login',
        'myApp.directives', // custom directives
        'ngGrid', // angular grid
        'ui', // angular ui
        'ngSanitize', // for html-bind in ckeditor
        'ui.bootstrap', // jquery ui bootstrap
        '$strap.directives' // angular strap
    ]);

// bootstrap angular
myApp.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {

    // TODO use html5 (no hash) where possible
    // $locationProvider.html5Mode(true);

    $routeProvider.when('/', {
        templateUrl: 'partials/index.html'
    });
    $routeProvider.when('/contact', {
        templateUrl: 'partials/contact.html'
    });
    $routeProvider.when('/about', {
        templateUrl: 'partials/about.html'
    });
    $routeProvider.when('/auth/github', {
        templateUrl: 'partials/serverGithubLogin.html'
    });

    // note that to minimize playground impact on app.js, we
    // are including just this simple route with a parameterized 
    // partial value (see playground.js and playground.html)
    $routeProvider.when('/playground/:widgetName', {
        templateUrl: 'playground/playground.html',
        controller: 'PlaygroundCtrl'
    });

    $routeProvider.when('/contacts', {
        templateUrl: 'contacts/index.html',
        controller: 'ContactsCtrl'
    });

    // by default, redirect to site root
    $routeProvider.otherwise({
        redirectTo: '/'
    });

}]);

// this is run after angular is instantiated and bootstrapped
myApp.run(function ($rootScope, $location, $http, $timeout, AuthService, RESTService, LocalService) {

    // *****
    // Eager load some data using simple REST client
    // *****
    $rootScope.restService = RESTService;
    $rootScope.LocalService = LocalService;

    // async load constants
    $rootScope.constants = [];
    $rootScope.LocalService.get('data/constants.json').success(function (data) {
        $rootScope.constants = data[0];
    });

    // *****
    // Initialize authentication
    // *****
    $rootScope.authService = AuthService;

    // text input for login/password (only)
    $rootScope.loginInput = 'git@github.com';
    $rootScope.passwordInput = 'git';

    $rootScope.$watch('authService.authorized()', function () {

        // if never logged in, do nothing (otherwise bookmarks fail)
        if ($rootScope.authService.initialState()) {
            // we are public browsing
            return;
        }

        // when user logs in, redirect to home
        if ($rootScope.authService.authorized()) {
            $location.path("/");
        }

        // when user logs out, redirect to home
        if (!$rootScope.authService.authorized()) {
            $location.path("/");
        }

    }, true);

});




