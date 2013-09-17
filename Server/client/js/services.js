'use strict';

// simple stub that could use a lot of work...
myApp.factory('RESTService',
    function ($http) {
        return {
            get: function (url) {
                return $http.get(url);
            },
            put: function (url, data) {
                return $http.put(url, data);
            },
            post: function (url, data) {
                return $http.post(url, data);
            },
            delete: function (url) {
                return $http({method: 'delete', url: url});
            }
        };
    }
);


// simple auth service that can use a lot of work... 
myApp.factory('AuthService',
    function () {
        var currentUser = null;
        var authorized = false;

        // initial state says we haven't logged in or out yet...
        // this tells us we are in public browsing
        var initialState = true;

        return {
            initialState: function () {
                return initialState;
            },
            login: function (name, password) {
                currentUser = name;
                authorized = true;
                //console.log("Logged in as " + name);
                initialState = false;
            },
            logout: function () {
                currentUser = null;
                authorized = false;
            },
            isLoggedIn: function () {
                return authorized;
            },
            currentUser: function () {
                return currentUser;
            },
            authorized: function () {
                return authorized;
            }
        };
    }
);

