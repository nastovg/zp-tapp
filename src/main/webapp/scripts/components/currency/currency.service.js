'use strict';

angular.module('tappApp')
    .factory('CurrencyService', function ($http) {
        return {
            findAll: function () {
                return $http.get('api/currency/all').then(function (response) {
                    return response.data;
                });
            },
            getLatestRates: function () {
                return $http.get('api/currency/latest').then(function (response) {
                    return response.data;
                });

            },
            getHistoricalRates: function (rateDate) {
                return $http.get('api/currency/historical', {
                    params: {
                        "date": rateDate
                    }
                }).then(function (response) {
                    return response.data;
                });

            },
            convert: function (units, fromCurrency, toCurrency, exchageRates) {
                if (fromCurrency == exchageRates.base) {
                    return (units * exchageRates.exchangeMap[toCurrency]);
                }
                var baseUnits = (units / exchageRates.exchangeMap[fromCurrency]);
                return (baseUnits * exchageRates.exchangeMap[toCurrency]);
            }
        };
    });
