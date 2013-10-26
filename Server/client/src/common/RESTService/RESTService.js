angular.module('RESTService', [])

    .factory('RESTService', function ($http, $cookieStore) {
        var rootURL = "http://10.100.0.167:8081";
        var token = $cookieStore.get('token') || 0;
        $cookieStore.remove('token');

        return {
            setToken: function (value) {
                token = value;
            },
            get: function (url) {
                return $http.get(rootURL + url + '?access_token=' + token);
            },
            put: function (url, data) {
                return $http.put(rootURL + url + '?access_token=' + token, data);
            },
            post: function (url, data) {
                return $http.post(rootURL + url + '?access_token=' + token, data);
            },
            remove: function (url) {
                return $http({method: 'delete', url: rootURL + url + '?access_token=' + token});
            }
        };
    })
;

