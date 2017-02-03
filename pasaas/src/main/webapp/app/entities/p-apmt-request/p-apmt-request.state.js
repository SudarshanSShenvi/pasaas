(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-apmt-request', {
            parent: 'entity',
            url: '/p-apmt-request',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAPMTRequest.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-apmt-request/p-apmt-requests.html',
                    controller: 'PAPMTRequestController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAPMTRequest');
                    $translatePartialLoader.addPart('pMTCategory');
                    $translatePartialLoader.addPart('pMTStatus');
                    $translatePartialLoader.addPart('pMTPriority');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-apmt-request-detail', {
            parent: 'entity',
            url: '/p-apmt-request/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAPMTRequest.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-apmt-request/p-apmt-request-detail.html',
                    controller: 'PAPMTRequestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAPMTRequest');
                    $translatePartialLoader.addPart('pMTCategory');
                    $translatePartialLoader.addPart('pMTStatus');
                    $translatePartialLoader.addPart('pMTPriority');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAPMTRequest', function($stateParams, PAPMTRequest) {
                    return PAPMTRequest.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-apmt-request',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-apmt-request-detail.edit', {
            parent: 'p-apmt-request-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-apmt-request/p-apmt-request-dialog.html',
                    controller: 'PAPMTRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAPMTRequest', function(PAPMTRequest) {
                            return PAPMTRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-apmt-request.new', {
            parent: 'p-apmt-request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-apmt-request/p-apmt-request-dialog.html',
                    controller: 'PAPMTRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                category: null,
                                pmtdescription: null,
                                pmtreason: null,
                                pmttitle: null,
                                pmttype: null,
                                siteid: null,
                                sitename: null,
                                sitepriority: null,
                                pmtstatus: null,
                                pmtpriority: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-apmt-request', null, { reload: 'p-apmt-request' });
                }, function() {
                    $state.go('p-apmt-request');
                });
            }]
        })
        .state('p-apmt-request.edit', {
            parent: 'p-apmt-request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-apmt-request/p-apmt-request-dialog.html',
                    controller: 'PAPMTRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAPMTRequest', function(PAPMTRequest) {
                            return PAPMTRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-apmt-request', null, { reload: 'p-apmt-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-apmt-request.delete', {
            parent: 'p-apmt-request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-apmt-request/p-apmt-request-delete-dialog.html',
                    controller: 'PAPMTRequestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAPMTRequest', function(PAPMTRequest) {
                            return PAPMTRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-apmt-request', null, { reload: 'p-apmt-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
