'use strict';

angular.module('tappApp')
    .controller('ConversionQueryController', function ($scope, $state, ConversionQuery) {

        $scope.conversionQuerys = [];
        $scope.loadAll = function () {
            ConversionQuery.query(function (result) {
                $scope.conversionQuerys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.conversionQuery = {
                amount: null,
                fromCurrency: null,
                toCurrency: null,
                conversionDate: null,
                createdOn: null,
                id: null
            };
        };
    });
