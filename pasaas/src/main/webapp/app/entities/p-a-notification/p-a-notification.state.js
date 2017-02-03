(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-notification', {
            parent: 'entity',
            url: '/p-a-notification',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pANotification.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-notification/p-a-notifications.html',
                    controller: 'PANotificationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pANotification');
                    $translatePartialLoader.addPart('pANotifType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-notification-detail', {
            parent: 'entity',
            url: '/p-a-notification/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pANotification.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-notification/p-a-notification-detail.html',
                    controller: 'PANotificationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pANotification');
                    $translatePartialLoader.addPart('pANotifType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PANotification', function($stateParams, PANotification) {
                    return PANotification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-notification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-notification-detail.edit', {
            parent: 'p-a-notification-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-notification/p-a-notification-dialog.html',
                    controller: 'PANotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PANotification', function(PANotification) {
                            return PANotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-notification.new', {
            parent: 'p-a-notification',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-notification/p-a-notification-dialog.html',
                    controller: 'PANotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                notiftype: null,
                                msgto: null,
                                msgcc: null,
                                msgsubject: null,
                                msgbody: null,
                                msgtouchtime: null,
                                msgattachments: null,
                                msgattachmentsContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-notification', null, { reload: 'p-a-notification' });
                }, function() {
                    $state.go('p-a-notification');
                });
            }]
        })
        .state('p-a-notification.edit', {
            parent: 'p-a-notification',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-notification/p-a-notification-dialog.html',
                    controller: 'PANotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PANotification', function(PANotification) {
                            return PANotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-notification', null, { reload: 'p-a-notification' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-notification.delete', {
            parent: 'p-a-notification',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-notification/p-a-notification-delete-dialog.html',
                    controller: 'PANotificationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PANotification', function(PANotification) {
                            return PANotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-notification', null, { reload: 'p-a-notification' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
