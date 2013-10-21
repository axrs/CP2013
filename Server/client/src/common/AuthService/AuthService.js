angular.module('AuthService', [])

    .factory('AuthService', function () {
        var currentUser = null;
        var authorized = false;

        var initialState = true;

        return {
            initialState: function () {
                return initialState;
            },
            githubLogin: function () {
                $.oauthpopup({
                    path: 'http://localhost:8081/api/auth/github/login',
                    callback: function () {
                        window.location.reload();
                    }
                });
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
    })
;
