angular.module('ngBoilerplate.providers', [
        'ui.router',
        'placeholders',
        'ui.bootstrap',
        'RESTService',
        'LocalService',
        'AuthService',
        'ngGrid', // angular grid
        'ui', // angular ui
        'ui.bootstrap', // jquery ui bootstrap
        '$strap.directives'
    ])

    .config(function config($stateProvider) {

        var access = routingConfig.accessLevels;

        $stateProvider.state('providers', {
            access: access.public,
            url: '/providers',
            views: {
                "main": {
                    controller: 'ProvidersCtrl',
                    templateUrl: 'providers/providers.index.tpl.html'
                }
            },
            data: { pageTitle: 'Staff' }
        });
    })
    .controller('ProvidersCtrl', function ProvidersCtrl($rootScope, $scope, RESTService, AuthService) {


        $scope.action = 'Editing';

        $scope.provider = null;

        $scope.process = function () {
            if ($scope.provider.providerId === 0) {
                $scope.create();
            } else {
                $scope.update();
            }
        };

        $scope.prepareNew = function () {
            $scope.clear();
            $scope.provider = {};
            $scope.provider.providerId = 0;
            $scope.provider.hours = [];
            for (var i = 0; i < 7; i++) {
                $scope.provider.hours.push({day: i, start: "00:00", breakStart: "00:00", breakEnd: "00:00", end: "00:00"});
            }
            $scope.action = 'New';
        };

        $scope.create = function () {
            RESTService.put('/api/providers', $scope.provider).
                success(function (data, status, headers, config) {
                    if (status == 201) {
                        $scope.provider = data;
                        $scope.providers.push($scope.provider);
                        $scope.clear();
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $rootScope.addError('Form Error:', 'A provider must have a Name and Surname.');
                            break;
                        case 409:
                            $rootScope.addError('Conflict Error:', 'A provider with the specified name exists.');
                            break;
                        case 500:
                            $rootScope.addError('Database Error:', 'Error creating the provider.');
                            break;
                    }
                });
        };

        $scope.update = function () {
            RESTService.put('/api/providers/' + $scope.provider.providerId, $scope.provider).
                success(function (data, status, headers, config) {
                    if (status == 202) {
                        $scope.provider = data;
                        $scope.clear();
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $rootScope.addError('Form Error:', 'A provider must have a Name and Surname.');
                            break;
                        case 409:
                            $rootScope.addError('Conflict Error:', 'A provider with the specified name exists.');
                            break;
                        case 500:
                            $rootScope.addError('Database Error:', 'Error creating the provider.');
                            break;
                    }
                });
        };

        $scope.clear = function () {
            $scope.provider = null;
            $scope.selectedProviders.splice(0, 1);
        };

        $scope.remove = function () {
            if ($scope.provider.providerId > 0) {
                RESTService.remove('/api/providers/' + $scope.provider.providerId).
                    success(function (data, status, headers, config) {
                        var index = $scope.providers.indexOf($scope.provider);
                        $scope.providers.splice(index, 1);
                        $scope.clear();
                    }).
                    error(function (data, status, headers, config) {
                        switch (status) {
                            case 501:
                                $rootScope.addError('Deletion Error:', 'Method not Implemented.');
                        }
                    });
            }
        };


        /*
         * Contact Grid Server Side Paging Example
         */

        $scope.filterOptions = {
            filterText: "",
            useExternalFilter: true
        };
        $scope.totalServerItems = 0;
        $scope.pagingOptions = {
            pageSizes: [50, 100, 150, 200, 250, 500],
            pageSize: 50,
            currentPage: 1
        };
        $scope.setPagingData = function (data, page, pageSize) {
            var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
            $scope.providers = pagedData;
            $scope.totalServerItems = data.length;
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        };
        $scope.getPagedDataAsync = function (pageSize, page, searchText) {
            setTimeout(function () {
                var data;
                if (searchText) {
                    var ft = searchText.toLowerCase();
                    RESTService.get('/api/providers').success(function (largeLoad) {
                        data = largeLoad.filter(function (item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data, page, pageSize);
                    });
                } else {
                    RESTService.get('/api/providers').success(function (largeLoad) {
                        $scope.setPagingData(largeLoad, page, pageSize);
                    });
                }
            }, 100);
        };

        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

        $scope.$watch('pagingOptions', function (newVal, oldVal) {
            if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
            }
        }, true);
        $scope.$watch('filterOptions', function (newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
            }
        }, true);

        $scope.selectedProviders = [];

        $scope.workDays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];


        $scope.gridOptions = {
            data: 'providers',
            enablePaging: false,
            showFooter: true,
            multiSelect: false,
            totalServerItems: 'totalServerItems',
            pagingOptions: $scope.pagingOptions,
            filterOptions: $scope.filterOptions,
            selectedItems: $scope.selectedProviders,
            columnDefs: [
                {field: 'name', displayName: 'Name'},
                {field: 'surname', displayName: 'Surname'},
                {field: 'company', displayName: 'Company'}

            ],
            afterSelectionChange: function () {
                if ($scope.selectedProviders.length == 1) {
                    $scope.provider = $scope.selectedProviders[0];
                    console.log($scope.provider);
                    $scope.action = 'Editing';
                } else {
                    $scope.provider = null;
                }
            }
        };
    });

