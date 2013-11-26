angular.module('AuthService', [])

    .factory('AuthService', function ($http, $cookieStore, $rootScope, RESTService) {

        var accessLevels = routingConfig.accessLevels,
            userRoles = routingConfig.userRoles,
            currentUser = $cookieStore.get('user') || { role: userRoles.public };
        $cookieStore.remove('user');

        function changeUser(user) {
            user.role = (user.isAdmin) ? userRoles.admin : userRoles.user;
            user.strategyDataDecode = JSON.parse(user.strategyData);
            _.extend(currentUser, user);
        }

        return {
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
            login: function (user, success, error) {
                $http.post('/login', user).success(function (user) {
                    changeUser(user);
                    success(user);
                }).error(error);
            },
            logout: function (success, error) {
                $http.post('/logout').success(function () {
                    changeUser({
                        username: '',
                        role: userRoles.public
                    });
                    success();
                }).error(error);
            },
            loginGitHub: function (success, error) {
                var popup = window.open('http://10.100.0.167:8081/api/auth/github/login', 'LoginWithGitHub', 'location=0,status=0,width=1020,height=590');

                var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
                var eventer = window[eventMethod];
                var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";

                eventer(messageEvent, function (e) {
                    RESTService.setToken(e.data);
                    popup.close();

                    RESTService.get('/api/user').
                        success(function (data, status) {
                            if (status == 200) {
                                changeUser(data);
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

angular.module('AuthService')
    .factory('Users', function ($http) {
        return {
            getAll: function (success, error) {
                $http.get('/users').success(success).error(error);
            }
        };
    });