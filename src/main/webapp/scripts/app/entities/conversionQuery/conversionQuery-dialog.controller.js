'use strict';

angular.module('tappApp').controller('ConversionQueryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ConversionQuery', 'User',
        function ($scope, $stateParams, $uibModalInstance, entity, ConversionQuery, User) {

            $scope.conversionQuery = entity;
            $scope.users = User.query();
            $scope.load = function (id) {
                ConversionQuery.get({id: id}, function (result) {
                    $scope.conversionQuery = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('tappApp:conversionQueryUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.conversionQuery.id != null) {
                    ConversionQuery.update($scope.conversionQuery, onSaveSuccess, onSaveError);
                } else {
                    ConversionQuery.save($scope.conversionQuery, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]);
