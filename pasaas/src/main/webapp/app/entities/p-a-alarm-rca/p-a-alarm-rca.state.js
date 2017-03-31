(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-alarm-rca', {
            parent: 'entity',
            url: '/p-a-alarm-rca',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAAlarmRCA.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-alarm-rca/p-a-alarm-rcas.html',
                    controller: 'PAAlarmRCAController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAAlarmRCA');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-alarm-rca-detail', {
            parent: 'entity',
            url: '/p-a-alarm-rca/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAAlarmRCA.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-alarm-rca/p-a-alarm-rca-detail.html',
                    controller: 'PAAlarmRCADetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAAlarmRCA');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAAlarmRCA', function($stateParams, PAAlarmRCA) {
                    return PAAlarmRCA.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-alarm-rca',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-alarm-rca-detail.edit', {
            parent: 'p-a-alarm-rca-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-alarm-rca/p-a-alarm-rca-dialog.html',
                    controller: 'PAAlarmRCADialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAAlarmRCA', function(PAAlarmRCA) {
                            return PAAlarmRCA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-alarm-rca.new', {
            parent: 'p-a-alarm-rca',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-alarm-rca/p-a-alarm-rca-dialog.html',
                    controller: 'PAAlarmRCADialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                alarmno: null,
                                alarmtext: null,
                                alarmtype: null,
                                rcdetails: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-alarm-rca', null, { reload: 'p-a-alarm-rca' });
                }, function() {
                    $state.go('p-a-alarm-rca');
                });
            }]
        })
        .state('p-a-alarm-rca.edit', {
            parent: 'p-a-alarm-rca',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-alarm-rca/p-a-alarm-rca-dialog.html',
                    controller: 'PAAlarmRCADialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAAlarmRCA', function(PAAlarmRCA) {
                            return PAAlarmRCA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-alarm-rca', null, { reload: 'p-a-alarm-rca' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-alarm-rca.delete', {
            parent: 'p-a-alarm-rca',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-alarm-rca/p-a-alarm-rca-delete-dialog.html',
                    controller: 'PAAlarmRCADeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAAlarmRCA', function(PAAlarmRCA) {
                            return PAAlarmRCA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-alarm-rca', null, { reload: 'p-a-alarm-rca' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
