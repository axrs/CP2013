'use strict';

// simple stub that could use a lot of work...
myApp.factory('RESTService',
    function ($http) {
        return {
            rootURL: "http://127.0.0.1:8081",
            get: function (url) {
                return $http.get(this.rootURL + url);
            },
            put: function (url, data) {
                return $http.put(this.rootURL + url, data);
            },
            post: function (url, data) {
                return $http.post(this.rootURL + url, data);
            },
            delete: function (url) {
                return $http({method: 'delete', url: this.rootURL + url});
            }
        };
    }
);
myApp.factory('LocalService',
    function ($http) {
        return {
            get: function (url) {
                return $http.get(url);
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
                console.log("Logged in as " + name);
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

