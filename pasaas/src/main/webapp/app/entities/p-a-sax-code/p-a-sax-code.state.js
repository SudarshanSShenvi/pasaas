(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-sax-code', {
            parent: 'entity',
            url: '/p-a-sax-code',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pASaxCode.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-sax-code/p-a-sax-codes.html',
                    controller: 'PASaxCodeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pASaxCode');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-sax-code-detail', {
            parent: 'entity',
            url: '/p-a-sax-code/{id}',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pASaxCode.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-sax-code/p-a-sax-code-detail.html',
                    controller: 'PASaxCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pASaxCode');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PASaxCode', function($stateParams, PASaxCode) {
                    return PASaxCode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-sax-code',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-sax-code-detail.edit', {
            parent: 'p-a-sax-code-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-sax-code/p-a-sax-code-dialog.html',
                    controller: 'PASaxCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PASaxCode', function(PASaxCode) {
                            return PASaxCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-sax-code.new', {
            parent: 'p-a-sax-code',
            url: '/new',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-sax-code/p-a-sax-code-dialog.html',
                    controller: 'PASaxCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                distalarm: null,
                                saxcode: null,
                                total: null,
                                painterval: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-sax-code', null, { reload: 'p-a-sax-code' });
                }, function() {
                    $state.go('p-a-sax-code');
                });
            }]
        })
        .state('p-a-sax-code.edit', {
            parent: 'p-a-sax-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-sax-code/p-a-sax-code-dialog.html',
                    controller: 'PASaxCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PASaxCode', function(PASaxCode) {
                            return PASaxCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-sax-code', null, { reload: 'p-a-sax-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-sax-code.delete', {
            parent: 'p-a-sax-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-sax-code/p-a-sax-code-delete-dialog.html',
                    controller: 'PASaxCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PASaxCode', function(PASaxCode) {
                            return PASaxCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-sax-code', null, { reload: 'p-a-sax-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
