(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-prediction', {
            parent: 'entity',
            url: '/p-a-prediction',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAPrediction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-prediction/p-a-predictions.html',
                    controller: 'PAPredictionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAPrediction');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-prediction-detail', {
            parent: 'entity',
            url: '/p-a-prediction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAPrediction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-prediction/p-a-prediction-detail.html',
                    controller: 'PAPredictionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAPrediction');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAPrediction', function($stateParams, PAPrediction) {
                    return PAPrediction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-prediction',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-prediction-detail.edit', {
            parent: 'p-a-prediction-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-prediction/p-a-prediction-dialog.html',
                    controller: 'PAPredictionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAPrediction', function(PAPrediction) {
                            return PAPrediction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-prediction.new', {
            parent: 'p-a-prediction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-prediction/p-a-prediction-dialog.html',
                    controller: 'PAPredictionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                predictiondate: null,
                                nename: null,
                                faulttype: null,
                                severity: null,
                                siteid: null,
                                sitepriority: null,
                                totalprediction: null,
                                failcount: null,
                                nofailcount: null,
                                failprob: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-prediction', null, { reload: 'p-a-prediction' });
                }, function() {
                    $state.go('p-a-prediction');
                });
            }]
        })
        .state('p-a-prediction.edit', {
            parent: 'p-a-prediction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-prediction/p-a-prediction-dialog.html',
                    controller: 'PAPredictionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAPrediction', function(PAPrediction) {
                            return PAPrediction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-prediction', null, { reload: 'p-a-prediction' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-prediction.delete', {
            parent: 'p-a-prediction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-prediction/p-a-prediction-delete-dialog.html',
                    controller: 'PAPredictionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAPrediction', function(PAPrediction) {
                            return PAPrediction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-prediction', null, { reload: 'p-a-prediction' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
