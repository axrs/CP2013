angular.module('RESTService', [])

    .factory('RESTService', function ($http) {
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
            remove: function (url) {
                return $http({method: 'delete', url: this.rootURL + url});
            }
        };
    })
;

