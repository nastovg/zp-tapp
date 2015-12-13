'use strict';

angular.module('tappApp')
    .controller('ConversionQueryDetailController', function ($scope, $rootScope, $stateParams, entity, ConversionQuery, User) {
        $scope.conversionQuery = entity;
        $scope.load = function (id) {
            ConversionQuery.get({id: id}, function (result) {
                $scope.conversionQuery = result;
            });
        };
        var unsubscribe = $rootScope.$on('tappApp:conversionQueryUpdate', function (event, result) {
            $scope.conversionQuery = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
