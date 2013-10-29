angular.module('RESTService', [])

    .factory('RESTService', function ($http, $cookieStore) {
        var rootURL = "http://10.100.0.136:8081";
        var token = $cookieStore.get('token') || 0;

        return {
            setToken: function (value) {
                token = value;
                $cookieStore.put('token', token);
            },
            get: function (url) {
                if (url.indexOf("/", this.length - 1) != -1) {
                    url += "/";
                }
                return $http.get(rootURL + url + '?access_token=' + token);
            },
            put: function (url, data) {
                if (url.indexOf("/", this.length - 1) != -1) {
                    url += "/";
                }
                return $http.put(rootURL + url + '?access_token=' + token, data);
            },
            post: function (url, data) {
                if (url.indexOf("/", this.length - 1) != -1) {
                    url += "/";
                }
                return $http.post(rootURL + url + '?access_token=' + token, data);
            },
            remove: function (url) {
                if (url.indexOf("/", this.length - 1) != -1) {
                    url += "/";
                }
                return $http({method: 'delete', url: rootURL + url + '?access_token=' + token});
            }
        };
    })
;

