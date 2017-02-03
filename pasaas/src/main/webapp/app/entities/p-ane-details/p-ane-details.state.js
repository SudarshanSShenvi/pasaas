(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-ane-details', {
            parent: 'entity',
            url: '/p-ane-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pANEDetails.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-ane-details/p-ane-details.html',
                    controller: 'PANEDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pANEDetails');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-ane-details-detail', {
            parent: 'entity',
            url: '/p-ane-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pANEDetails.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-ane-details/p-ane-details-detail.html',
                    controller: 'PANEDetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pANEDetails');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PANEDetails', function($stateParams, PANEDetails) {
                    return PANEDetails.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-ane-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-ane-details-detail.edit', {
            parent: 'p-ane-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-ane-details/p-ane-details-dialog.html',
                    controller: 'PANEDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PANEDetails', function(PANEDetails) {
                            return PANEDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-ane-details.new', {
            parent: 'p-ane-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-ane-details/p-ane-details-dialog.html',
                    controller: 'PANEDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                distinguishedname: null,
                                siteid: null,
                                sitename: null,
                                sitelocation: null,
                                sitezone: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-ane-details', null, { reload: 'p-ane-details' });
                }, function() {
                    $state.go('p-ane-details');
                });
            }]
        })
        .state('p-ane-details.edit', {
            parent: 'p-ane-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-ane-details/p-ane-details-dialog.html',
                    controller: 'PANEDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PANEDetails', function(PANEDetails) {
                            return PANEDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-ane-details', null, { reload: 'p-ane-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-ane-details.delete', {
            parent: 'p-ane-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-ane-details/p-ane-details-delete-dialog.html',
                    controller: 'PANEDetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PANEDetails', function(PANEDetails) {
                            return PANEDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-ane-details', null, { reload: 'p-ane-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
