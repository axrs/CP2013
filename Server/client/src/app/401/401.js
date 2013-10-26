angular.module('ngBoilerplate.401', [
        'ui.router',
        'placeholders',
        'ui.bootstrap'
    ])

    .config(function config($stateProvider) {
        var access = routingConfig.accessLevels;

        $stateProvider.state('401', {
            access: access.public,
            url: '/401',
            views: {
                "main": {
                    controller: 'FourOOneCtrl',
                    templateUrl: '401/401.tpl.html'
                }
            },
            data: { pageTitle: 'Looking for Something?' }
        });
    })

    .controller('FourOOneCtrl', function FourOOneCtrl($scope) {

    })

;
