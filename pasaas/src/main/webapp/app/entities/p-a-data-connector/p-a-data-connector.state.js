(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-data-connector', {
            parent: 'entity',
            url: '/p-a-data-connector',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pADataConnector.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-data-connector/p-a-data-connectors.html',
                    controller: 'PADataConnectorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pADataConnector');
                    $translatePartialLoader.addPart('dCType');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-data-connector-detail', {
            parent: 'entity',
            url: '/p-a-data-connector/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pADataConnector.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-data-connector/p-a-data-connector-detail.html',
                    controller: 'PADataConnectorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pADataConnector');
                    $translatePartialLoader.addPart('dCType');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PADataConnector', function($stateParams, PADataConnector) {
                    return PADataConnector.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-data-connector',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-data-connector-detail.edit', {
            parent: 'p-a-data-connector-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-data-connector/p-a-data-connector-dialog.html',
                    controller: 'PADataConnectorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PADataConnector', function(PADataConnector) {
                            return PADataConnector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-data-connector.new', {
            parent: 'p-a-data-connector',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-data-connector/p-a-data-connector-dialog.html',
                    controller: 'PADataConnectorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dcname: null,
                                dctype: null,
                                urllink: null,
                                remoteuname: null,
                                remotepwd: null,
                                remoteip: null,
                                port: null,
                                localpath: null,
                                jsrpwd: null,
                                jsruser: null,
                                destinationpath: null,
                                retrieve: null,
                                retrievedays: null,
                                mode: null,
                                datamode: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-data-connector', null, { reload: 'p-a-data-connector' });
                }, function() {
                    $state.go('p-a-data-connector');
                });
            }]
        })
        .state('p-a-data-connector.edit', {
            parent: 'p-a-data-connector',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-data-connector/p-a-data-connector-dialog.html',
                    controller: 'PADataConnectorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PADataConnector', function(PADataConnector) {
                            return PADataConnector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-data-connector', null, { reload: 'p-a-data-connector' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-data-connector.delete', {
            parent: 'p-a-data-connector',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-data-connector/p-a-data-connector-delete-dialog.html',
                    controller: 'PADataConnectorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PADataConnector', function(PADataConnector) {
                            return PADataConnector.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-data-connector', null, { reload: 'p-a-data-connector' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
