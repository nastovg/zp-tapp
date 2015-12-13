'use strict';

angular.module('tappApp')
    .controller('ConversionQueryDeleteController', function ($scope, $uibModalInstance, entity, ConversionQuery) {

        $scope.conversionQuery = entity;
        $scope.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ConversionQuery.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
