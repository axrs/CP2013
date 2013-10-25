angular.module('ngBoilerplate.404', [
        'ui.router',
        'placeholders',
        'ui.bootstrap'
    ])

    .config(function config($stateProvider) {
        var access = routingConfig.accessLevels;

        $stateProvider.state('404', {
            access: access.public,
            url: '/404',
            views: {
                "main": {
                    controller: 'FourOFourCtrl',
                    templateUrl: '404/404.tpl.html'
                }
            },
            data: { pageTitle: 'Looking for Something?' }
        });
    })

    .controller('FourOFourCtrl', function FourOFourCtrl($scope) {

    })

;
