(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-organization', {
            parent: 'entity',
            url: '/p-a-organization',
            data: {
                authorities: ['ROLE_USER'],
                // authorities: [],
                pageTitle: 'pasaasApp.pAOrganization.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-organization/p-a-organizations.html',
                    controller: 'PAOrganizationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAOrganization');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-organization-detail', {
            parent: 'entity',
            url: '/p-a-organization/{id}',
            data: {
                authorities: ['ROLE_USER'],
                // authorities: [],
                pageTitle: 'pasaasApp.pAOrganization.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-organization/p-a-organization-detail.html',
                    controller: 'PAOrganizationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAOrganization');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAOrganization', function($stateParams, PAOrganization) {
                    return PAOrganization.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-organization',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-organization-detail.edit', {
            parent: 'p-a-organization-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
                // authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
                    controller: 'PAOrganizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAOrganization', function(PAOrganization) {
                            return PAOrganization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-organization.new', {
            parent: 'p-a-organization',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
                // authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
                    controller: 'PAOrganizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                organization: null,
                                validfrom: null,
                                validto: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-organization', null, { reload: 'p-a-organization' });
                }, function() {
                    $state.go('p-a-organization');
                });
            }]
        })
        .state('p-a-organization.edit', {
            parent: 'p-a-organization',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
                // authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
                    controller: 'PAOrganizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAOrganization', function(PAOrganization) {
                            return PAOrganization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-organization', null, { reload: 'p-a-organization' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-organization.delete', {
            parent: 'p-a-organization',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
                // authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-organization/p-a-organization-delete-dialog.html',
                    controller: 'PAOrganizationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAOrganization', function(PAOrganization) {
                            return PAOrganization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-organization', null, { reload: 'p-a-organization' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
