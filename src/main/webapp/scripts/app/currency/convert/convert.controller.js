'use strict';

angular.module('tappApp')
    .controller('ConvertController', function ($scope, $filter, Principal, DateUtils, CurrencyService, ConversionQuery) {
        //default values
        $scope.currentConversionQuery = {
            id: null,
            fromCurrency: "EUR",
            toCurrency: "USD",
            amount: 100,
            conversionDate: new Date(),
            createdOn: new Date()
        }
        var allowedMaxDate = new Date();
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
                CurrencyService.getHistoricalRates($scope.currentConversionQuery.conversionDate).then(function (data) {
                    var resultValue = CurrencyService.convert($scope.currentConversionQuery, data);
                    $scope.resultAmount = $filter('currency')(resultValue, ' ', 2);
                    if (!$scope.selectedConversion && !$scope.startConversion) {
                        $scope.startConversion = false;
                        saveConversionQuery();
                    }
                    $scope.selectedConversion = false;
                    $scope.startConversion = false;
                });
            }
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
