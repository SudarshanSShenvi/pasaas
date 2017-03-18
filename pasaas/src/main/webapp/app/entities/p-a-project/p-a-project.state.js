(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-project', {
            parent: 'entity',
            url: '/p-a-project',
            data: {
                // authorities: ['ROLE_USER'],
                authorities: [],
                pageTitle: 'pasaasApp.pAProject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-project/p-a-projects.html',
                    controller: 'PAProjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAProject');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-project-detail', {
            parent: 'entity',
            url: '/p-a-project/{id}',
            data: {
                // authorities: ['ROLE_USER'],
                authorities: [],
                pageTitle: 'pasaasApp.pAProject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-project/p-a-project-detail.html',
                    controller: 'PAProjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAProject');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAProject', function($stateParams, PAProject) {
                    return PAProject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-project',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-project-detail.edit', {
            parent: 'p-a-project-detail',
            url: '/detail/edit',
            data: {
                // authorities: ['ROLE_USER']
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-project/p-a-project-dialog.html',
                    controller: 'PAProjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAProject', function(PAProject) {
                            return PAProject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-project.new', {
            parent: 'p-a-project',
            url: '/new',
            data: {
                // authorities: ['ROLE_USER']
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-project/p-a-project-dialog.html',
                    controller: 'PAProjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                projectname: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-project', null, { reload: 'p-a-project' });
                }, function() {
                    $state.go('p-a-project');
                });
            }]
        })
        .state('p-a-project.edit', {
            parent: 'p-a-project',
            url: '/{id}/edit',
            data: {
                // authorities: ['ROLE_USER']
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-project/p-a-project-dialog.html',
                    controller: 'PAProjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAProject', function(PAProject) {
                            return PAProject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-project', null, { reload: 'p-a-project' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-project.delete', {
            parent: 'p-a-project',
            url: '/{id}/delete',
            data: {
                // authorities: ['ROLE_USER']
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-project/p-a-project-delete-dialog.html',
                    controller: 'PAProjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAProject', function(PAProject) {
                            return PAProject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-project', null, { reload: 'p-a-project' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
