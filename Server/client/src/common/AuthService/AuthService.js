angular.module('AuthService', [])

    .factory('AuthService', function ($http, $cookieStore, $rootScope, RESTService, $location) {

        var accessLevels = routingConfig.accessLevels,
            userRoles = routingConfig.userRoles,
            currentUser = $cookieStore.get('user') || { role: userRoles.public };

        function changeUser(newUser) {
            $cookieStore.remove('user');

            newUser.role = userRoles.public;

            RESTService.setToken(newUser.token);
            if (newUser.isAdmin === 0) {
                newUser.role = userRoles.user;
            } else if (newUser.isAdmin === 1) {
                newUser.role = userRoles.admin;
            }

            if (newUser.strategyData && newUser.strategyData !== "") {
                newUser.strategyDataDecode = JSON.parse(newUser.strategyData);
            }

            _.extend(currentUser, newUser);


            $cookieStore.put('user', currentUser);
            $location.path('/');
        }

        return {
            setUser: function (value) {
                this.user = value;
            },
            authorize: function (accessLevel, role) {
                if (role === undefined) {
                    role = currentUser.role;
                }
                return accessLevel.bitMask & role.bitMask;
            },
            isLoggedIn: function (user) {
                if (user === undefined) {
                    user = currentUser;
                }
                return currentUser.role.title == userRoles.user.title || currentUser.role.title == userRoles.admin.title;
            },
            register: function (user, success, error) {
                $http.post('/register', user).success(function (res) {
                    changeUser(res);
                    success();
                }).error(error);
            },
            plainTextLogin: function (username, password) {
                var user = {};
                user.userName = username;
                user.password = password;

                RESTService.put('/api/user/login', user).
                    success(function (data, status, headers, config) {
                        if (status == 202) {
                            changeUser(data);
                        }
                    }).error(function (data, status) {
                        switch (status) {
                            case 500:
                                $rootScope.addError("Database Error:", "There was an error attempting login.  Please try again later.");
                                break;
                            case 409:
                                $rootScope.addError("Invalid Credentials:", "The specified username and/or password are incorrect.");

                                break;
                            case 404:
                                $rootScope.addError("Invalid Credentials:", "The specified username and/or password are incorrect.");
                                break;
                        }
                    });
            },
            login: function (user) {
                RESTService.put('/api/user/login', user).
                    success(function (data, status, headers, config) {
                        if (status == 202) {
                            changeUser(data);
                        }
                    }).
                    error(function (data, status) {
                        switch (status) {
                            case 500:
                                $rootScope.addError("Database Error:", "There was an error attempting login.  Please try again later.");
                                break;
                            case 409:
                                $rootScope.addError("Invalid Credentials:", "The specified username and/or password are incorrect.");

                                break;
                            case 404:
                                $rootScope.addError("Invalid Credentials:", "The specified username and/or password are incorrect.");
                                break;
                        }
                    });

            },
            logout: function (success) {
                console.log('LoggingOut');
                changeUser({
                    userName: '',
                    isAdmin: -1,
                    role: userRoles.public
                });
            },
            loginGitHub: function (success, error) {
                var popup = window.open('http://10.100.0.136:8081/api/auth/github/login', 'LoginWithGitHub', 'location=0,status=0,width=1020,height=590');

                var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
                var eventer = window[eventMethod];
                var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";

                eventer(messageEvent, function (e) {
                    RESTService.setToken(e.data);
                    popup.close();

                    RESTService.get('/api/user').
                        success(function (data, status) {
                            if (status == 202) {
                                changeUser(data);
                                console.log(data);
                                if (success) {
                                    success(data);
                                }
                            }
                        }).error(error);
                }, false);

            },
            accessLevels: accessLevels,
            userRoles: userRoles,
            user: currentUser
        };
    });