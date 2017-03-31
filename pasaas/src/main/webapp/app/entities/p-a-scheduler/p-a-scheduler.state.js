(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-scheduler', {
            parent: 'entity',
            url: '/p-a-scheduler',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAScheduler.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-scheduler/p-a-schedulers.html',
                    controller: 'PASchedulerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAScheduler');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-scheduler-detail', {
            parent: 'entity',
            url: '/p-a-scheduler/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAScheduler.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-scheduler/p-a-scheduler-detail.html',
                    controller: 'PASchedulerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAScheduler');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAScheduler', function($stateParams, PAScheduler) {
                    return PAScheduler.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-scheduler',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-scheduler-detail.edit', {
            parent: 'p-a-scheduler-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-scheduler/p-a-scheduler-dialog.html',
                    controller: 'PASchedulerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAScheduler', function(PAScheduler) {
                            return PAScheduler.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-scheduler.new', {
            parent: 'p-a-scheduler',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-scheduler/p-a-scheduler-dialog.html',
                    controller: 'PASchedulerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                intervaltime: null,
                                hourval: null,
                                minutesval: null,
                                runsunday: null,
                                runmonday: null,
                                runtuesday: null,
                                runwednesday: null,
                                runthursday: null,
                                runfriday: null,
                                dcname: null,
                                runsaturday: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-scheduler', null, { reload: 'p-a-scheduler' });
                }, function() {
                    $state.go('p-a-scheduler');
                });
            }]
        })
        .state('p-a-scheduler.edit', {
            parent: 'p-a-scheduler',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-scheduler/p-a-scheduler-dialog.html',
                    controller: 'PASchedulerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAScheduler', function(PAScheduler) {
                            return PAScheduler.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-scheduler', null, { reload: 'p-a-scheduler' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-scheduler.delete', {
            parent: 'p-a-scheduler',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-scheduler/p-a-scheduler-delete-dialog.html',
                    controller: 'PASchedulerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAScheduler', function(PAScheduler) {
                            return PAScheduler.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-scheduler', null, { reload: 'p-a-scheduler' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
