'use strict';

angular.module('tappApp')
    .controller('ConvertController', function ($scope, $filter, CurrencyService) {
        $scope.fromCurrency = "EUR";
        $scope.toCurrency = "USD";
        $scope.units = 1;
        $scope.rateDate = new Date();

        CurrencyService.findAll().then(function (data) {
            $scope.currencies = data;
            $scope.convert();

        });
        $scope.convert = function () {
            CurrencyService.getHistoricalRates($scope.rateDate).then(function (data) {
                var resultValue = CurrencyService.convert($scope.units, $scope.fromCurrency, $scope.toCurrency, data);
                $scope.resultUnit = $filter('currency')(resultValue, ' ', 2);
            });
        }


    });
