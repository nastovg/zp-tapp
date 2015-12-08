'use strict';

angular.module('tappApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


