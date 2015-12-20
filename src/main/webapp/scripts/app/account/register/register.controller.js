'use strict';

angular.module('tappApp')
    .controller('RegisterController', function ($scope, $timeout, Auth) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        var userdob = new Date();
        userdob.setYear(userdob.getFullYear() + 1)

        // The oldest allowed subscriber can be 140 years old.
        $scope.minAllowedBirthDate = new Date();
        $scope.minAllowedBirthDate.setYear($scope.minAllowedBirthDate.getFullYear() - 140);

        // The youngest allowed subscriber can be 6 years old.
        $scope.maxAllowedBirthDate = new Date();
        $scope.maxAllowedBirthDate.setYear($scope.maxAllowedBirthDate.getFullYear() - 6);

        $timeout(function () {
            angular.element('[ng-model="registerAccount.login"]').focus();
        });

        $scope.register = function () {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.registerAccount.langKey = 'en';
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;

                Auth.createAccount($scope.registerAccount).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    });
