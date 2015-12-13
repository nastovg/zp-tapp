'use strict';

angular.module('tappApp')
    .factory('ConversionQuery', function ($resource, DateUtils) {
        return $resource('api/conversionQuerys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'latest': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.conversionDate = DateUtils.convertDateTimeFromServer(data.conversionDate);
                    data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                    return data;
                }
            },
            'update': { method: 'PUT' }
        });
    });
