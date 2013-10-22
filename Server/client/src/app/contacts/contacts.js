angular.module('ngBoilerplate.contacts', [
        'ui.router',
        'placeholders',
        'ui.bootstrap',
        'RESTService',
        'LocalService',
        'AuthService',
        'ngGrid', // angular grid
        'ui', // angular ui
        'ui.bootstrap' // jquery ui bootstrap
    ])

    .config(function config($stateProvider) {

        var access = routingConfig.accessLevels;

        $stateProvider.state('contacts', {
            access: access.user,
            url: '/contacts',
            views: {
                "main": {
                    controller: 'ContactsCtrl',
                    templateUrl: 'contacts/contacts.index.tpl.html'
                }
            },
            data: { pageTitle: 'Contacts' }
        });
    })
    .controller('ContactsCtrl', function ContactsController($scope, RESTService) {

        $scope.action = 'Editing';
        $scope.alerts = [];

        $scope.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.contact = null;

        $scope.process = function () {
            if ($scope.contact.contactId === 0) {
                $scope.create();
            } else {
                $scope.update();
            }
        };

        $scope.prepareNew = function () {
            $scope.clear();
            $scope.contact = {};
            $scope.contact.contactId = 0;
            $scope.action = 'New';
        };

        $scope.create = function () {
            RESTService.put('/api/contacts', $scope.contact).
                success(function (data, status, headers, config) {
                    if (status == 201) {
                        $scope.contact = data;
                        $scope.contacts.push($scope.contact);
                        $scope.clear();
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $scope.alerts.push({type: 'error', title: 'Form Error:', msg: "A contact must have a First and Last name."});
                            break;
                        case 409:
                            $scope.alerts.push({type: 'error', title: 'Conflict:', msg: "A contact with the specified name exists."});
                            break;
                        case 500:
                            $scope.alerts.push({type: 'error', title: 'Database Error:', msg: "Error creating the contact."});
                            break;
                    }
                });
        };

        $scope.update = function () {
            RESTService.put('/api/contacts/' + $scope.contact.contactId, $scope.contact).
                success(function (data, status, headers, config) {
                    if (status == 202) {
                        $scope.contact = data;
                        $scope.clear();
                    }
                }).
                error(function (data, status, headers, config) {
                    switch (status) {
                        case 400:
                            $scope.alerts.push({type: 'error', title: 'Form Error:', msg: "A contact must have a First and Last name."});
                            break;
                        case 409:
                            $scope.alerts.push({type: 'error', title: 'Conflict:', msg: "A contact with the specified name exists."});
                            break;
                        case 500:
                            $scope.alerts.push({type: 'error', title: 'Database Error:', msg: "Error creating the contact."});
                            break;
                    }
                });
        };

        $scope.clear = function () {
            $scope.contact = null;
            $scope.selectedContacts.splice(0, 1);
        };

        $scope.remove = function () {
            if ($scope.contact.contactId > 0) {
                RESTService.remove('/api/contacts/' + $scope.contact.contactId).
                    success(function (data, status, headers, config) {
                        var index = $scope.contacts.indexOf($scope.contact);
                        $scope.contacts.splice(index, 1);
                        $scope.clear();
                    }).
                    error(function (data, status, headers, config) {
                        switch (status) {
                            case 501:
                                $scope.alerts.push({type: 'error', title: 'Deletion Error:', msg: "Method not Implemented"});
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
            $scope.contacts = pagedData;
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
                    RESTService.get('/api/contacts').success(function (largeLoad) {
                        data = largeLoad.filter(function (item) {
                            return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data, page, pageSize);
                    });
                } else {
                    RESTService.get('/api/contacts').success(function (largeLoad) {
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

        $scope.selectedContacts = [];

        $scope.gridOptions = {
            data: 'contacts',
            enablePaging: false,
            showFooter: true,
            multiSelect: false,
            totalServerItems: 'totalServerItems',
            pagingOptions: $scope.pagingOptions,
            filterOptions: $scope.filterOptions,
            selectedItems: $scope.selectedContacts,
            columnDefs: [
                {field: 'name', displayName: 'Name'},
                {field: 'surname', displayName: 'Surname'}
            ],
            afterSelectionChange: function () {
                if ($scope.selectedContacts.length == 1) {
                    $scope.contact = $scope.selectedContacts[0];
                    $scope.action = 'Editing';
                } else {
                    $scope.contact = null;
                }
            }
        };
    });

