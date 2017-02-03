(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-file-upload', {
            parent: 'entity',
            url: '/p-a-file-upload',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAFileUpload.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-file-upload/p-a-file-uploads.html',
                    controller: 'PAFileUploadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAFileUpload');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-file-upload-detail', {
            parent: 'entity',
            url: '/p-a-file-upload/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAFileUpload.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-file-upload/p-a-file-upload-detail.html',
                    controller: 'PAFileUploadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAFileUpload');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAFileUpload', function($stateParams, PAFileUpload) {
                    return PAFileUpload.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-file-upload',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-file-upload-detail.edit', {
            parent: 'p-a-file-upload-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-file-upload/p-a-file-upload-dialog.html',
                    controller: 'PAFileUploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAFileUpload', function(PAFileUpload) {
                            return PAFileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-file-upload.new', {
            parent: 'p-a-file-upload',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-file-upload/p-a-file-upload-dialog.html',
                    controller: 'PAFileUploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                remotepwd: null,
                                portno: null,
                                filepath: null,
                                transfertype: null,
                                remoteipaddr: null,
                                remoteuser: null,
                                scheduledprocess: null,
                                mapreduce: null,
                                filetype: null,
                                customseparator: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-file-upload', null, { reload: 'p-a-file-upload' });
                }, function() {
                    $state.go('p-a-file-upload');
                });
            }]
        })
        .state('p-a-file-upload.edit', {
            parent: 'p-a-file-upload',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-file-upload/p-a-file-upload-dialog.html',
                    controller: 'PAFileUploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAFileUpload', function(PAFileUpload) {
                            return PAFileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-file-upload', null, { reload: 'p-a-file-upload' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-file-upload.delete', {
            parent: 'p-a-file-upload',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-file-upload/p-a-file-upload-delete-dialog.html',
                    controller: 'PAFileUploadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAFileUpload', function(PAFileUpload) {
                            return PAFileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-file-upload', null, { reload: 'p-a-file-upload' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
