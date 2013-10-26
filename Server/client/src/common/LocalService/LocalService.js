angular.module('LocalService', [])

    .factory('LocalService', function ($http) {
        return {
            get: function (url) {
                return $http.get(url);
            }
        };
    })
;

