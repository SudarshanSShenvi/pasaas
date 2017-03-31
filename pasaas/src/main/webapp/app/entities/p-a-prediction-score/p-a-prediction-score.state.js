(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-prediction-score', {
            parent: 'entity',
            url: '/p-a-prediction-score',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAPredictionScore.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-prediction-score/p-a-prediction-scores.html',
                    controller: 'PAPredictionScoreController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAPredictionScore');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-prediction-score-detail', {
            parent: 'entity',
            url: '/p-a-prediction-score/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAPredictionScore.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-prediction-score/p-a-prediction-score-detail.html',
                    controller: 'PAPredictionScoreDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAPredictionScore');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAPredictionScore', function($stateParams, PAPredictionScore) {
                    return PAPredictionScore.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-prediction-score',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-prediction-score-detail.edit', {
            parent: 'p-a-prediction-score-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-prediction-score/p-a-prediction-score-dialog.html',
                    controller: 'PAPredictionScoreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAPredictionScore', function(PAPredictionScore) {
                            return PAPredictionScore.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-prediction-score.new', {
            parent: 'p-a-prediction-score',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-prediction-score/p-a-prediction-score-dialog.html',
                    controller: 'PAPredictionScoreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dist: null,
                                alarmno: null,
                                bcount: null,
                                ccount: null,
                                bscore: null,
                                cscore: null,
                                createdon: null,
                                zaxis: null,
                                date: null,
                                severity: null,
                                painterval: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-prediction-score', null, { reload: 'p-a-prediction-score' });
                }, function() {
                    $state.go('p-a-prediction-score');
                });
            }]
        })
        .state('p-a-prediction-score.edit', {
            parent: 'p-a-prediction-score',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-prediction-score/p-a-prediction-score-dialog.html',
                    controller: 'PAPredictionScoreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAPredictionScore', function(PAPredictionScore) {
                            return PAPredictionScore.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-prediction-score', null, { reload: 'p-a-prediction-score' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-prediction-score.delete', {
            parent: 'p-a-prediction-score',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-prediction-score/p-a-prediction-score-delete-dialog.html',
                    controller: 'PAPredictionScoreDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAPredictionScore', function(PAPredictionScore) {
                            return PAPredictionScore.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-prediction-score', null, { reload: 'p-a-prediction-score' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
