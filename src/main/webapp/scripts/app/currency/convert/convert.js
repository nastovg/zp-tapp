'use strict';

angular.module('tappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('convert', {
                parent: 'currency',
                url: '/convert',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Currency Convert'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/currency/convert/convert.html',
                        controller: 'ConvertController'
                    }
                },
                resolve: {

                }
            });
    });

