'use strict';

angular.module('tappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('currency', {
                abstract: true,
                parent: 'site'
            });
    });
