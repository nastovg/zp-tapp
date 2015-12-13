'use strict';

angular.module('tappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('conversionQuery', {
                parent: 'entity',
                url: '/conversionQuerys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ConversionQuerys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/conversionQuery/conversionQuerys.html',
                        controller: 'ConversionQueryController'
                    }
                },
                resolve: {
                }
            })
            .state('conversionQuery.detail', {
                parent: 'entity',
                url: '/conversionQuery/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ConversionQuery'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/conversionQuery/conversionQuery-detail.html',
                        controller: 'ConversionQueryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ConversionQuery', function ($stateParams, ConversionQuery) {
                        return ConversionQuery.get({id: $stateParams.id});
                    }]
                }
            })
            .state('conversionQuery.new', {
                parent: 'conversionQuery',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/conversionQuery/conversionQuery-dialog.html',
                        controller: 'ConversionQueryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    amount: null,
                                    fromCurrency: null,
                                    toCurrency: null,
                                    conversionDate: null,
                                    createdOn: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('conversionQuery', null, { reload: true });
                        }, function () {
                            $state.go('conversionQuery');
                        })
                }]
            })
            .state('conversionQuery.edit', {
                parent: 'conversionQuery',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/conversionQuery/conversionQuery-dialog.html',
                        controller: 'ConversionQueryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ConversionQuery', function (ConversionQuery) {
                                return ConversionQuery.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('conversionQuery', null, { reload: true });
                        }, function () {
                            $state.go('^');
                        })
                }]
            })
            .state('conversionQuery.delete', {
                parent: 'conversionQuery',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/conversionQuery/conversionQuery-delete-dialog.html',
                        controller: 'ConversionQueryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ConversionQuery', function (ConversionQuery) {
                                return ConversionQuery.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('conversionQuery', null, { reload: true });
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
