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
            convert: function (conversionQuery, exchageRates) {
                if (conversionQuery.fromCurrency == exchageRates.base) {
                    return (conversionQuery.amount * exchageRates.exchangeMap[conversionQuery.toCurrency]);
                }
                var baseUnits = (conversionQuery.amount / exchageRates.exchangeMap[conversionQuery.fromCurrency]);
                return (baseUnits * exchageRates.exchangeMap[conversionQuery.toCurrency]);
            }
        };
    });
