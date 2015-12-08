'use strict';

angular.module('tappApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function (response) {
                var alertKey = response.headers('X-tappApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param: response.headers('X-tappApp-params')});
                }
                return response;
            }
        };
    });
