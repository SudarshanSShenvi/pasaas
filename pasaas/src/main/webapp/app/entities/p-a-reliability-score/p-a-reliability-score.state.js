(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-reliability-score', {
            parent: 'entity',
            url: '/p-a-reliability-score',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAReliabilityScore.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-reliability-score/p-a-reliability-scores.html',
                    controller: 'PAReliabilityScoreController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAReliabilityScore');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-reliability-score-detail', {
            parent: 'entity',
            url: '/p-a-reliability-score/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAReliabilityScore.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-reliability-score/p-a-reliability-score-detail.html',
                    controller: 'PAReliabilityScoreDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAReliabilityScore');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAReliabilityScore', function($stateParams, PAReliabilityScore) {
                    return PAReliabilityScore.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-reliability-score',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-reliability-score-detail.edit', {
            parent: 'p-a-reliability-score-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-reliability-score/p-a-reliability-score-dialog.html',
                    controller: 'PAReliabilityScoreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAReliabilityScore', function(PAReliabilityScore) {
                            return PAReliabilityScore.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-reliability-score.new', {
            parent: 'p-a-reliability-score',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-reliability-score/p-a-reliability-score-dialog.html',
                    controller: 'PAReliabilityScoreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                predictedalarms: null,
                                occuredalarms: null,
                                matchedalarms: null,
                                created: null,
                                predictedpercentage: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-reliability-score', null, { reload: 'p-a-reliability-score' });
                }, function() {
                    $state.go('p-a-reliability-score');
                });
            }]
        })
        .state('p-a-reliability-score.edit', {
            parent: 'p-a-reliability-score',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-reliability-score/p-a-reliability-score-dialog.html',
                    controller: 'PAReliabilityScoreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAReliabilityScore', function(PAReliabilityScore) {
                            return PAReliabilityScore.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-reliability-score', null, { reload: 'p-a-reliability-score' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-reliability-score.delete', {
            parent: 'p-a-reliability-score',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-reliability-score/p-a-reliability-score-delete-dialog.html',
                    controller: 'PAReliabilityScoreDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAReliabilityScore', function(PAReliabilityScore) {
                            return PAReliabilityScore.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-reliability-score', null, { reload: 'p-a-reliability-score' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
