'use strict';

angular.module('tappApp')
    .controller('ConvertController', function ($scope, $filter, Principal, DateUtils, CurrencyService, ConversionQuery) {
        //default values
        $scope.currentConversionQuery = {
            id: null,
            fromCurrency: "EUR",
            toCurrency: "USD",
            amount: 100,
            result: 0,
            conversionDate: new Date(),
            createdOn: new Date()
        }
        var allowedMaxDate = new Date();
        var cachedExchangeRates = {};
        //Set tomorrow as max allowed date.
        allowedMaxDate.setDate(allowedMaxDate.getDate() + 1);
        $scope.maxAllowedDate = allowedMaxDate;
        $scope.errorSaving = false;
        $scope.startConversion = true;




        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.currentConversionQuery.owner = angular.copy(account);
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        CurrencyService.findAll().then(function (data) {
            $scope.currencies = data;
            $scope.convert();

        });
        $scope.convert = function () {
            if ($scope.conversionForm.$valid) {
                var formatedConversionDate = formatDate($scope.currentConversionQuery.conversionDate);
                var cachedExchangeRate = cachedExchangeRates[formatedConversionDate];
                if (cachedExchangeRate) {
                    convertWithExchangeRate(cachedExchangeRate);
                } else {
                    CurrencyService.getHistoricalRates($scope.currentConversionQuery.conversionDate).then(function (data) {
                        cachedExchangeRates[formatedConversionDate] = data;
                        convertWithExchangeRate(data);
                    });
                }
            }
        }

        var formatDate = function(date) {
            var formatedDate = $filter('date')(date, 'dd-MM-yyyy');
            return formatedDate;
        }

        var convertWithExchangeRate = function(exchangeRate) {
            $scope.currentConversionQuery.result = CurrencyService.convert($scope.currentConversionQuery, exchangeRate);
            $scope.resultAmount = $filter('currency')($scope.currentConversionQuery.result, ' ', 2);
            if (!$scope.selectedConversion && !$scope.startConversion) {
                $scope.startConversion = false;
                saveConversionQuery();
            }
            $scope.selectedConversion = false;
            $scope.startConversion = false;
        }

        $scope.conversionQuerys = [];
        $scope.loadLatest = function () {
            ConversionQuery.latest(function (result) {
                if ($scope.conversionQuerys != null) {
                    $scope.conversionQuerys = [];
                }
                $scope.conversionQuerys = result;
            });
        };
        $scope.loadLatest();


        var onSaveSuccess = function (result) {
            $scope.isSaving = false;
            $scope.loadLatest();
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $scope.errorSaving = true;
        };

        var saveConversionQuery = function () {
            $scope.isSaving = true;
            ConversionQuery.save($scope.currentConversionQuery, onSaveSuccess, onSaveError);
        }

        $scope.selectQuery = function (selectedQuery) {
            $scope.currentConversionQuery = angular.copy(selectedQuery);
            $scope.currentConversionQuery.conversionDate = DateUtils.convertDateTimeFromServer(selectedQuery.conversionDate);
            $scope.currentConversionQuery.id = null;
            $scope.currentConversionQuery.owner = $scope.account;
            $scope.currentConversionQuery.createdOn = new Date();
            $scope.selectedConversion = true;
            $scope.convert();
        }

    });
