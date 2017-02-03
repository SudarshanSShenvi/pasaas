(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-error-message', {
            parent: 'entity',
            url: '/p-a-error-message',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAErrorMessage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-error-message/p-a-error-messages.html',
                    controller: 'PAErrorMessageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAErrorMessage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-error-message-detail', {
            parent: 'entity',
            url: '/p-a-error-message/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAErrorMessage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-error-message/p-a-error-message-detail.html',
                    controller: 'PAErrorMessageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAErrorMessage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAErrorMessage', function($stateParams, PAErrorMessage) {
                    return PAErrorMessage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-error-message',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-error-message-detail.edit', {
            parent: 'p-a-error-message-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-error-message/p-a-error-message-dialog.html',
                    controller: 'PAErrorMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAErrorMessage', function(PAErrorMessage) {
                            return PAErrorMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-error-message.new', {
            parent: 'p-a-error-message',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-error-message/p-a-error-message-dialog.html',
                    controller: 'PAErrorMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                errorcode: null,
                                errormsg: null,
                                errortime: null,
                                erroruser: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-error-message', null, { reload: 'p-a-error-message' });
                }, function() {
                    $state.go('p-a-error-message');
                });
            }]
        })
        .state('p-a-error-message.edit', {
            parent: 'p-a-error-message',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-error-message/p-a-error-message-dialog.html',
                    controller: 'PAErrorMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAErrorMessage', function(PAErrorMessage) {
                            return PAErrorMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-error-message', null, { reload: 'p-a-error-message' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-error-message.delete', {
            parent: 'p-a-error-message',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-error-message/p-a-error-message-delete-dialog.html',
                    controller: 'PAErrorMessageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAErrorMessage', function(PAErrorMessage) {
                            return PAErrorMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-error-message', null, { reload: 'p-a-error-message' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
