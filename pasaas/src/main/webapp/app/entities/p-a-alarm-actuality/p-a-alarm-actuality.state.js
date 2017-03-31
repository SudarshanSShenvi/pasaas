(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-alarm-actuality', {
            parent: 'entity',
            url: '/p-a-alarm-actuality',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAAlarmActuality.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-alarm-actuality/p-a-alarm-actualities.html',
                    controller: 'PAAlarmActualityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAAlarmActuality');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-alarm-actuality-detail', {
            parent: 'entity',
            url: '/p-a-alarm-actuality/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAAlarmActuality.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-alarm-actuality/p-a-alarm-actuality-detail.html',
                    controller: 'PAAlarmActualityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAAlarmActuality');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAAlarmActuality', function($stateParams, PAAlarmActuality) {
                    return PAAlarmActuality.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-alarm-actuality',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-alarm-actuality-detail.edit', {
            parent: 'p-a-alarm-actuality-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-alarm-actuality/p-a-alarm-actuality-dialog.html',
                    controller: 'PAAlarmActualityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAAlarmActuality', function(PAAlarmActuality) {
                            return PAAlarmActuality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-alarm-actuality.new', {
            parent: 'p-a-alarm-actuality',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-alarm-actuality/p-a-alarm-actuality-dialog.html',
                    controller: 'PAAlarmActualityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nename: null,
                                faulttype: null,
                                severity: null,
                                siteid: null,
                                sitepriority: null,
                                predictionmatched: null,
                                occured: null,
                                pmtcreated: null,
                                totalevents: null,
                                failedcountmatch: null,
                                nofailcountmatch: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-alarm-actuality', null, { reload: 'p-a-alarm-actuality' });
                }, function() {
                    $state.go('p-a-alarm-actuality');
                });
            }]
        })
        .state('p-a-alarm-actuality.edit', {
            parent: 'p-a-alarm-actuality',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-alarm-actuality/p-a-alarm-actuality-dialog.html',
                    controller: 'PAAlarmActualityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAAlarmActuality', function(PAAlarmActuality) {
                            return PAAlarmActuality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-alarm-actuality', null, { reload: 'p-a-alarm-actuality' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-alarm-actuality.delete', {
            parent: 'p-a-alarm-actuality',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-alarm-actuality/p-a-alarm-actuality-delete-dialog.html',
                    controller: 'PAAlarmActualityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAAlarmActuality', function(PAAlarmActuality) {
                            return PAAlarmActuality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-alarm-actuality', null, { reload: 'p-a-alarm-actuality' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
