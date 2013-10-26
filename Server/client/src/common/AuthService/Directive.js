angular.module('AuthService')
    .directive('accessLevel', ['AuthService', function (AuthService) {
        return {
            restrict: 'A',
            link: function ($scope, element, attrs) {
                var prevDisp = element.css('display');
                var userRole;
                var accessLevel;

                $scope.user = AuthService.user;
                $scope.$watch('user', function (user) {
                    if (user.role) {
                        userRole = user.role;
                    }
                    updateCSS();
                }, true);

                attrs.$observe('accessLevel', function (al) {
                    if (al) {
                        accessLevel = $scope.$eval(al);
                    }
                    updateCSS();
                });

                function updateCSS() {
                    if (userRole && accessLevel) {
                        if (!AuthService.authorize(accessLevel, userRole)) {
                            element.css('display', 'none');
                        }
                        else {
                            element.css('display', prevDisp);
                        }
                    }
                }
            }
        };
    }]);