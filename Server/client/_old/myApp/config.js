angular.module('configuration', [])
/**
 * @ngdoc object
 * @name configuration.CONFIG
 *
 * @description
 * Application configuration. This is a constant, not a service (but ngdocs won't let me
 * group it as a constant).
 *
 */
    .constant('CONFIG',
    {
        /**
         * @ngdoc property
         * @name configuration.CONFIG#oauth_api_endpoint
         * @propertyOf configuration.CONFIG
         * @description
         * OAuth API endpoint. This would be something like
         * <code>https://www.googleapis.com/api/v1</code>. Do *NOT* include a trailing slash.
         */
        oauth_api_endpoint: 'http://localhost:8081/api',

        /**
         * @ngdoc property
         * @name configuration.CONFIG#oauth_callback_url
         * @propertyOf configuration.CONFIG
         * @description
         * OAuth URL to redirect callbacks to. This would be something like
         * <code>http://www.myapp.com/oauth_callback</code>. Do *NOT* include a trailing slash.
         */
        oauth_callback_url: 'http://localhost/github_callback'
    });