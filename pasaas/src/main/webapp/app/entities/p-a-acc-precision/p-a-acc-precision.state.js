(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-acc-precision', {
            parent: 'entity',
            url: '/p-a-acc-precision',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAAccPrecision.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-acc-precision/p-a-acc-precisions.html',
                    controller: 'PAAccPrecisionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAAccPrecision');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-acc-precision-detail', {
            parent: 'entity',
            url: '/p-a-acc-precision/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAAccPrecision.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-acc-precision/p-a-acc-precision-detail.html',
                    controller: 'PAAccPrecisionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAAccPrecision');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAAccPrecision', function($stateParams, PAAccPrecision) {
                    return PAAccPrecision.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-acc-precision',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-acc-precision-detail.edit', {
            parent: 'p-a-acc-precision-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-acc-precision/p-a-acc-precision-dialog.html',
                    controller: 'PAAccPrecisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAAccPrecision', function(PAAccPrecision) {
                            return PAAccPrecision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-acc-precision.new', {
            parent: 'p-a-acc-precision',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-acc-precision/p-a-acc-precision-dialog.html',
                    controller: 'PAAccPrecisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                totalpredictions: null,
                                failpredictions: null,
                                nofailpredictions: null,
                                totalevents: null,
                                pfailmatched: null,
                                pnofailmatched: null,
                                predictiondate: null,
                                accuracyval: null,
                                prcisionval: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-acc-precision', null, { reload: 'p-a-acc-precision' });
                }, function() {
                    $state.go('p-a-acc-precision');
                });
            }]
        })
        .state('p-a-acc-precision.edit', {
            parent: 'p-a-acc-precision',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-acc-precision/p-a-acc-precision-dialog.html',
                    controller: 'PAAccPrecisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAAccPrecision', function(PAAccPrecision) {
                            return PAAccPrecision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-acc-precision', null, { reload: 'p-a-acc-precision' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-acc-precision.delete', {
            parent: 'p-a-acc-precision',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-acc-precision/p-a-acc-precision-delete-dialog.html',
                    controller: 'PAAccPrecisionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAAccPrecision', function(PAAccPrecision) {
                            return PAAccPrecision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-acc-precision', null, { reload: 'p-a-acc-precision' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
