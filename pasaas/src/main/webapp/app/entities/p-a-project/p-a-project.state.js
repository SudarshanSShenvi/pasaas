(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider

// .state('company_pages.pdf23', {
//     url: '/pdf23',
//     templateUrl: 'company_pages/pdf23/pdf23.ng.html',
//     controller: 'controller_pdf23',
//     data: { pageTitle: 'pdf23' }
// })
// .state('company_pages.pdf23.step_one', {
//     url: '/step_one',
//     templateUrl: 'company_pages/step_one/step_one.ng.html',
//     controller: 'controller_step_one',
//     data: { pageTitle: 'step_one' }
// })
        .state('p-a-project.step_one', {
            parent: 'p-a-project',
            url: '/step_one',
            data: {
                authorities: ['ROLE_USER']
            },
            templateUrl: 'app/entities/p-a-project/step_one.html',
            controller: 'PAProjectController',
            controllerAs: 'vm'
        })
        .state('p-a-project.step_two', {
            parent: 'p-a-project',
            url: '/step_two',
            data: {
                authorities: ['ROLE_USER']
            },
            templateUrl: 'app/entities/p-a-project/step_two.html',
            controller: 'PAProjectController',
            controllerAs: 'vm'
        })
        .state('p-a-project.step_three', {
            parent: 'p-a-project',
            url: '/step_three',
            data: {
                authorities: ['ROLE_USER']
            },
            templateUrl: 'app/entities/p-a-project/step_three.html',
            controller: 'PAProjectController',
            controllerAs: 'vm'
        })
        .state('p-a-project.su', {
            parent: 'entity',
            url: '/p-a-project/suops',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pAProject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-project/p-a-projects.html',
                    controller: 'PAProjectControllerSu',
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
        
        .state('p-a-project', {
            parent: 'entity',
            url: '/p-a-project',
            data: {
                authorities: ['ROLE_USER'],
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
                authorities: ['ROLE_USER'],
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
                authorities: ['ROLE_USER']
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

        // .state('p-a-project.new.step_one', {
        //     parent: 'p-a-project.new',
        //     url: '/step_one',
        //     templateUrl: 'app/entities/p-a-project/step_one.html',
        //     controller: 'PAProjectDialogController',
        //     controllerAs: 'vm'
        // })
        // .state('p-a-project.new.step_two', {
        //     parent: 'p-a-project.new',
        //     url: '/step_two',
        //     templateUrl: 'app/entities/p-a-project/step_two.html',
        //     controller: 'PAProjectDialogController',
        //     controllerAs: 'vm'
        // })
        // .state('p-a-project.new.step_three', {
        //     parent: 'p-a-project.new',
        //     url: '/step_three',
        //     templateUrl: 'app/entities/p-a-project/step_three.html',
        //     controller: 'PAProjectDialogController',
        //     controllerAs: 'vm'
        // })
        // .state('p-a-project.new', {
        //     parent: 'p-a-project',
        //     url: '/new',
        //     data: {
        //         // authorities: ['ROLE_USER']
        //         authorities: []
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/p-a-project/p-a-project-dialog.html',
        //             controller: 'PAProjectDialogController',
        //             controllerAs: 'vm',
        //         }
        //     },
        //     resolve: {
        //         entity: function () {
        //             return {
        //                 projectname: null,
        //                 description: null,
        //                 id: null
        //             };
        //         }
        //     }
        // })

        .state('p-a-project.new', {
            parent: 'p-a-project',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', '$rootScope', function($stateParams, $state, $uibModal, $rootScope) {
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
                                id: null,
                                paorgpro: $rootScope.this_organization
                            };
                        }
                
                    }
                }).result.then(function() {
                    $state.go('p-a-organization', null, { reload: 'p-a-organization' });
                    // $state.go('p-a-project', null, { reload: 'p-a-project' });
                }, function() {
                    $state.go('p-a-organization');
                    // $state.go('p-a-project');
                });
            }]
        })
        // .state('p-a-project.new.step_one', {
        //     parent: 'p-a-project.new',
        //     url: '/step_one',
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'company_pages/step_one/step_one.ng.html',
        //             controller: 'PAProjectDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         projectname: null,
        //                         description: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('p-a-organization', null, { reload: 'p-a-project' });
        //             // $state.go('p-a-project', null, { reload: 'p-a-project' });
        //         }, function() {
        //             $state.go('p-a-organization');
        //             // $state.go('p-a-project');
        //         });
        //     }]
        //     // templateUrl: 'company_pages/step_one/step_one.ng.html',
        //     // controller: 'PAProjectController',
        //     // controllerAs: 'vm'
        // })
        // .state('p-a-project.new.step_two', {
        //     parent: 'p-a-project.new',
        //     url: '/step_two',
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'company_pages/step_two/step_two.ng.html',
        //             controller: 'PAProjectDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         projectname: null,
        //                         description: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('p-a-organization', null, { reload: 'p-a-project' });
        //             // $state.go('p-a-project', null, { reload: 'p-a-project' });
        //         }, function() {
        //             $state.go('p-a-organization');
        //             // $state.go('p-a-project');
        //         });
        //     }]
        //     // templateUrl: 'company_pages/step_two/step_two.ng.html',
        //     // controller: 'PAProjectController',
        //     // controllerAs: 'vm'
        // })
        

        // .state('p-a-project.edit.step_one', {
        //     parent: 'p-a-project.edit',
        //     url: '/step_one',
        //     templateUrl: 'app/entities/p-a-project/step_one.html',
        //     controller: 'PAProjectDialogController',
        //     controllerAs: 'vm'
        // })
        // .state('p-a-project.edit.step_two', {
        //     parent: 'p-a-project.edit',
        //     url: '/step_two',
        //     templateUrl: 'app/entities/p-a-project/step_two.html',
        //     controller: 'PAProjectDialogController',
        //     controllerAs: 'vm'
        // })
        // .state('p-a-project.edit.step_three', {
        //     parent: 'p-a-project.edit',
        //     url: '/step_three',
        //     templateUrl: 'app/entities/p-a-project/step_three.html',
        //     controller: 'PAProjectDialogController',
        //     controllerAs: 'vm'
        // })
        // .state('p-a-project.edit', {
        //     parent: 'p-a-project',
        //     url: '/{id}/edit',
        //     data: {
        //         // authorities: ['ROLE_USER']
        //         authorities: []
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/p-a-project/p-a-project-dialog.html',
        //             controller: 'PAProjectDialogController',
        //             controllerAs: 'vm',
        //         }
        //     },
        //     resolve: {
        //         entity: ['PAProject', function(PAProject) {
        //             return PAProject.get({id : $stateParams.id}).$promise;
        //         }]
        //     }
        // })
        .state('p-a-project.edit', {
            parent: 'p-a-project',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
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
                authorities: ['ROLE_USER']
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
