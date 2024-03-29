angular.module('ngBoilerplate.types', [
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

        $stateProvider.state('types', {
            access: access.admin,
            url: '/types',
            views: {
                "main": {
                    controller: 'TypesCtrl',
                    templateUrl: 'types/types.index.tpl.html'
                }
            },
            data: { pageTitle: 'Appointment Types' }
        });
    })
    .controller('TypesCtrl', function TypesCtrl($rootScope, $scope, RESTService) {

        $scope.action = 'Editing';
        $scope.type = {};

        $scope.process = function () {
            if ($scope.type.typeId === 0) {
                $scope.create();
            } else {
                $scope.update();
            }
        };

        $scope.prepareNew = function () {
            $scope.clear();
            $scope.type = {};
            $scope.type.typeId = 0;
            $scope.action = 'New';
        };

        $scope.create = function () {
            RESTService.put('/api/types', $scope.type).
                success(function (data, status, headers, config) {
                    if (status == 201) {
                        $scope.type = data;
                        $scope.types.push($scope.type);
                        $scope.clear();
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $rootScope.addError('Form Error:', 'An appointment type must have a description.');
                            break;
                        case 500:
                            $rootScope.addError('Database Error:', 'Error creating the appointment type.');
                            break;
                    }
                });
        };

        $scope.update = function () {
            RESTService.put('/api/types/' + $scope.type.typeId, $scope.type).
                success(function (data, status) {
                    if (status == 202) {
                        $scope.type = data;
                        $scope.clear();
                    }
                }).
                error(function (data, status) {
                    switch (status) {
                        case 400:
                            $rootScope.addError('Form Error:', 'An appointment type must have a description.');
                            break;
                        case 500:
                            $rootScope.addError('Database Error:', 'Error creating the appointment type.');
                            break;
                    }
                });
        };

        $scope.clear = function () {
            $scope.type = null;
            $scope.selected.splice(0, 1);
        };

        $scope.remove = function () {
            if ($scope.type.typeId > 0) {
                RESTService.remove('/api/types/' + $scope.type.typeId).
                    success(function () {
                        var index = $scope.types.indexOf($scope.type);
                        $scope.types.splice(index, 1);
                        $scope.clear();
                    }).
                    error(function (data, status) {
                        switch (status) {
                            case 501:
                                $rootScope.addError('Database Error:', 'Method not implemented.');
                        }
                    });
            }
        };

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
            $scope.types = pagedData;
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
                    RESTService.get('/api/types').success(function (largeLoad) {
                        data = largeLoad.filter(function (item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data, page, pageSize);
                    });
                } else {
                    RESTService.get('/api/types').success(function (largeLoad) {
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

        $scope.selected = [];

        $scope.gridOptions = {
            data: 'types',
            enablePaging: false,
            showFooter: true,
            multiSelect: false,
            totalServerItems: 'totalServerItems',
            pagingOptions: $scope.pagingOptions,
            filterOptions: $scope.filterOptions,
            selectedItems: $scope.selected,
            columnDefs: [
                {field: 'description', displayName: 'Description'},
                {field: 'duration', displayName: 'Duration'}
            ],
            afterSelectionChange: function () {
                if ($scope.selected.length == 1) {
                    $scope.type = $scope.selected[0];
                    $scope.action = 'Editing';
                } else {
                    $scope.type = null;
                }
            }
        };
    });
