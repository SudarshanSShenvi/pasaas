(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-reliability-conf', {
            parent: 'entity',
            url: '/p-a-reliability-conf',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pAReliabilityConf.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-reliability-conf/p-a-reliability-confs.html',
                    controller: 'PAReliabilityConfController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAReliabilityConf');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-reliability-conf-detail', {
            parent: 'entity',
            url: '/p-a-reliability-conf/{id}',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pAReliabilityConf.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-reliability-conf/p-a-reliability-conf-detail.html',
                    controller: 'PAReliabilityConfDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAReliabilityConf');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAReliabilityConf', function($stateParams, PAReliabilityConf) {
                    return PAReliabilityConf.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-reliability-conf',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-reliability-conf-detail.edit', {
            parent: 'p-a-reliability-conf-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-reliability-conf/p-a-reliability-conf-dialog.html',
                    controller: 'PAReliabilityConfDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAReliabilityConf', function(PAReliabilityConf) {
                            return PAReliabilityConf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-reliability-conf.new', {
            parent: 'p-a-reliability-conf',
            url: '/new',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-reliability-conf/p-a-reliability-conf-dialog.html',
                    controller: 'PAReliabilityConfDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                bscore: null,
                                cscore: null,
                                dataset: null,
                                category: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-reliability-conf', null, { reload: 'p-a-reliability-conf' });
                }, function() {
                    $state.go('p-a-reliability-conf');
                });
            }]
        })
        .state('p-a-reliability-conf.edit', {
            parent: 'p-a-reliability-conf',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-reliability-conf/p-a-reliability-conf-dialog.html',
                    controller: 'PAReliabilityConfDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAReliabilityConf', function(PAReliabilityConf) {
                            return PAReliabilityConf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-reliability-conf', null, { reload: 'p-a-reliability-conf' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-reliability-conf.delete', {
            parent: 'p-a-reliability-conf',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-reliability-conf/p-a-reliability-conf-delete-dialog.html',
                    controller: 'PAReliabilityConfDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAReliabilityConf', function(PAReliabilityConf) {
                            return PAReliabilityConf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-reliability-conf', null, { reload: 'p-a-reliability-conf' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
