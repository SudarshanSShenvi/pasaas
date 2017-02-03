(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-raw-alarm-data', {
            parent: 'entity',
            url: '/p-a-raw-alarm-data',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pARawAlarmData.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-raw-alarm-data/p-a-raw-alarm-data.html',
                    controller: 'PARawAlarmDataController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pARawAlarmData');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-raw-alarm-data-detail', {
            parent: 'entity',
            url: '/p-a-raw-alarm-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pARawAlarmData.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-raw-alarm-data/p-a-raw-alarm-data-detail.html',
                    controller: 'PARawAlarmDataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pARawAlarmData');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PARawAlarmData', function($stateParams, PARawAlarmData) {
                    return PARawAlarmData.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-raw-alarm-data',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-raw-alarm-data-detail.edit', {
            parent: 'p-a-raw-alarm-data-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-raw-alarm-data/p-a-raw-alarm-data-dialog.html',
                    controller: 'PARawAlarmDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PARawAlarmData', function(PARawAlarmData) {
                            return PARawAlarmData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-raw-alarm-data.new', {
            parent: 'p-a-raw-alarm-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-raw-alarm-data/p-a-raw-alarm-data-dialog.html',
                    controller: 'PARawAlarmDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                alarmno: null,
                                distname: null,
                                starteddate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-raw-alarm-data', null, { reload: 'p-a-raw-alarm-data' });
                }, function() {
                    $state.go('p-a-raw-alarm-data');
                });
            }]
        })
        .state('p-a-raw-alarm-data.edit', {
            parent: 'p-a-raw-alarm-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-raw-alarm-data/p-a-raw-alarm-data-dialog.html',
                    controller: 'PARawAlarmDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PARawAlarmData', function(PARawAlarmData) {
                            return PARawAlarmData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-raw-alarm-data', null, { reload: 'p-a-raw-alarm-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-raw-alarm-data.delete', {
            parent: 'p-a-raw-alarm-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-raw-alarm-data/p-a-raw-alarm-data-delete-dialog.html',
                    controller: 'PARawAlarmDataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PARawAlarmData', function(PARawAlarmData) {
                            return PARawAlarmData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-raw-alarm-data', null, { reload: 'p-a-raw-alarm-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
