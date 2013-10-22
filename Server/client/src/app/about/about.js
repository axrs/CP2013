angular.module('ngBoilerplate.about', [
        'ui.router',
        'placeholders',
        'ui.bootstrap'
    ])

    .config(function config($stateProvider) {
        var access = routingConfig.accessLevels;

        $stateProvider.state('about', {
            access: access.anon,
            url: '/about',
            views: {
                "main": {
                    controller: 'AboutCtrl',
                    templateUrl: 'about/about.tpl.html'
                }
            },
            data: { pageTitle: 'Who are We?' }
        });
    })

    .controller('AboutCtrl', function AboutCtrl($scope) {

    })

;
