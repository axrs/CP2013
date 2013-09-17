'use strict'

myApp.controller('InboxCtrl', ['$scope', function ($scope) {

    // modal
    $scope.open = function () {
        $scope.shouldBeOpen = true;
    };

    $scope.close = function () {
        $scope.closeMsg = 'I was closed at: ' + new Date();
        $scope.shouldBeOpen = false;
    };
    // end modal

    $scope.recentMail = [
        { type: 'unread', from: 'Phil Jenkins', msg: 'Your password will expire in 23 days' },
        { type: 'unread', from: 'Paul Terry', msg: 'CRM system updated.' },
        { type: 'read', from: 'Matt Lundquist', msg: 'Successfully deployed Oracle 11g' },
        { type: 'read', from: 'Lindsay Dugan', msg: 'Closed support ticket #4455' },
        { type: 'read', from: 'Lindsay Dugan', msg: 'Reminder: Offsite in San Diego (comicon)' }
    ];

    $scope.closeMail = function (index) {
        //console.log("closing mail " + index);
        $scope.recentMail.splice(index, 1);
    };

}]);

myApp.controller('ContactsCtrl', ['$scope', '$rootScope', function ($scope, $rootScope) {

    $scope.action = 'Editing';
    $scope.alerts = [];

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.contact = null;

    $scope.contacts = [];
    $scope.total = 0

    $rootScope.restService.get('/api/contacts', function (data) {
        $scope.contacts = data;
        $scope.total = $scope.contacts.length;
    });

    $scope.process = function () {
        ($scope.contact.id == 0) ? $scope.create() : $scope.update();
    };

    $scope.select = function (selectedContact) {
        $scope.contact = selectedContact;
        $scope.action = 'Editing';
    };

    $scope.new = function () {
        $scope.clear();
        $scope.contact = {};
        $scope.contact.id = 0;
        $scope.action = 'New';
    };

    $scope.create = function () {
        $rootScope.restService.put('/api/contacts', $scope.contact).
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

    $scope.clear = function () {
        $scope.contact = null;
        $scope.total = $scope.contacts.length;
    };

    $scope.delete = function () {
        if ($scope.contact.id > 0) {
            $rootScope.restService.delete('/api/contacts/' + $scope.contact.id).
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
}]);