(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-sax-code-tmp', {
            parent: 'entity',
            url: '/p-a-sax-code-tmp',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pASaxCodeTmp.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-sax-code-tmp/p-a-sax-code-tmps.html',
                    controller: 'PASaxCodeTmpController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pASaxCodeTmp');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-sax-code-tmp-detail', {
            parent: 'entity',
            url: '/p-a-sax-code-tmp/{id}',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pASaxCodeTmp.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-sax-code-tmp/p-a-sax-code-tmp-detail.html',
                    controller: 'PASaxCodeTmpDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pASaxCodeTmp');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PASaxCodeTmp', function($stateParams, PASaxCodeTmp) {
                    return PASaxCodeTmp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-sax-code-tmp',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-sax-code-tmp-detail.edit', {
            parent: 'p-a-sax-code-tmp-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-sax-code-tmp/p-a-sax-code-tmp-dialog.html',
                    controller: 'PASaxCodeTmpDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PASaxCodeTmp', function(PASaxCodeTmp) {
                            return PASaxCodeTmp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-sax-code-tmp.new', {
            parent: 'p-a-sax-code-tmp',
            url: '/new',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-sax-code-tmp/p-a-sax-code-tmp-dialog.html',
                    controller: 'PASaxCodeTmpDialogController',
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
                                severity: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-sax-code-tmp', null, { reload: 'p-a-sax-code-tmp' });
                }, function() {
                    $state.go('p-a-sax-code-tmp');
                });
            }]
        })
        .state('p-a-sax-code-tmp.edit', {
            parent: 'p-a-sax-code-tmp',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-sax-code-tmp/p-a-sax-code-tmp-dialog.html',
                    controller: 'PASaxCodeTmpDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PASaxCodeTmp', function(PASaxCodeTmp) {
                            return PASaxCodeTmp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-sax-code-tmp', null, { reload: 'p-a-sax-code-tmp' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-sax-code-tmp.delete', {
            parent: 'p-a-sax-code-tmp',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-sax-code-tmp/p-a-sax-code-tmp-delete-dialog.html',
                    controller: 'PASaxCodeTmpDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PASaxCodeTmp', function(PASaxCodeTmp) {
                            return PASaxCodeTmp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-sax-code-tmp', null, { reload: 'p-a-sax-code-tmp' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
