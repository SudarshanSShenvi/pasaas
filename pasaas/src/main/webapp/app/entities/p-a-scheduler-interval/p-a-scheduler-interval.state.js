(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-scheduler-interval', {
            parent: 'entity',
            url: '/p-a-scheduler-interval',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pASchedulerInterval.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-scheduler-interval/p-a-scheduler-intervals.html',
                    controller: 'PASchedulerIntervalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pASchedulerInterval');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-scheduler-interval-detail', {
            parent: 'entity',
            url: '/p-a-scheduler-interval/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pASchedulerInterval.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-scheduler-interval/p-a-scheduler-interval-detail.html',
                    controller: 'PASchedulerIntervalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pASchedulerInterval');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PASchedulerInterval', function($stateParams, PASchedulerInterval) {
                    return PASchedulerInterval.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-scheduler-interval',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-scheduler-interval-detail.edit', {
            parent: 'p-a-scheduler-interval-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-scheduler-interval/p-a-scheduler-interval-dialog.html',
                    controller: 'PASchedulerIntervalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PASchedulerInterval', function(PASchedulerInterval) {
                            return PASchedulerInterval.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-scheduler-interval.new', {
            parent: 'p-a-scheduler-interval',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-scheduler-interval/p-a-scheduler-interval-dialog.html',
                    controller: 'PASchedulerIntervalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                hourval: null,
                                minutesval: null,
                                schname: null,
                                dcname: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-scheduler-interval', null, { reload: 'p-a-scheduler-interval' });
                }, function() {
                    $state.go('p-a-scheduler-interval');
                });
            }]
        })
        .state('p-a-scheduler-interval.edit', {
            parent: 'p-a-scheduler-interval',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-scheduler-interval/p-a-scheduler-interval-dialog.html',
                    controller: 'PASchedulerIntervalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PASchedulerInterval', function(PASchedulerInterval) {
                            return PASchedulerInterval.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-scheduler-interval', null, { reload: 'p-a-scheduler-interval' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-scheduler-interval.delete', {
            parent: 'p-a-scheduler-interval',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-scheduler-interval/p-a-scheduler-interval-delete-dialog.html',
                    controller: 'PASchedulerIntervalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PASchedulerInterval', function(PASchedulerInterval) {
                            return PASchedulerInterval.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-scheduler-interval', null, { reload: 'p-a-scheduler-interval' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
