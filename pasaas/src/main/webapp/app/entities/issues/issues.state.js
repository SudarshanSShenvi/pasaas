(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('issues', {
            parent: 'entity',
            url: '/issues',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'imgcheckApp.issues.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/issues/issues.html',
                    controller: 'IssuesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('issues');
                    $translatePartialLoader.addPart('categoryType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('issues-detail', {
            parent: 'entity',
            url: '/issues/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'imgcheckApp.issues.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/issues/issues-detail.html',
                    controller: 'IssuesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('issues');
                    $translatePartialLoader.addPart('categoryType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Issues', function($stateParams, Issues) {
                    return Issues.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'issues',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('issues-detail.edit', {
            parent: 'issues-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/issues/issues-dialog.html',
                    controller: 'IssuesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Issues', function(Issues) {
                            return Issues.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('issues.new', {
            parent: 'issues',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/issues/issues-dialog.html',
                    controller: 'IssuesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                category: null,
                                description: null,
                                attachment: null,
                                attachmentContentType: null,
                                details: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('issues', null, { reload: true });
                }, function() {
                    $state.go('issues');
                });
            }]
        })
        .state('issues.edit', {
            parent: 'issues',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/issues/issues-dialog.html',
                    controller: 'IssuesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Issues', function(Issues) {
                            return Issues.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('issues', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('issues.delete', {
            parent: 'issues',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/issues/issues-delete-dialog.html',
                    controller: 'IssuesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Issues', function(Issues) {
                            return Issues.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('issues', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
