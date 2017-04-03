(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-business-plan', {
            parent: 'entity',
            url: '/p-a-business-plan',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pABusinessPlan.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-business-plan/p-a-business-plans.html',
                    controller: 'PABusinessPlanController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pABusinessPlan');
                    $translatePartialLoader.addPart('planType');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-business-plan-detail', {
            parent: 'entity',
            url: '/p-a-business-plan/{id}',
            data: {
                authorities: ['ROLE_SUPERADMIN'],
                pageTitle: 'pasaasApp.pABusinessPlan.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-detail.html',
                    controller: 'PABusinessPlanDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pABusinessPlan');
                    $translatePartialLoader.addPart('planType');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PABusinessPlan', function($stateParams, PABusinessPlan) {
                    return PABusinessPlan.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-business-plan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-business-plan-detail.edit', {
            parent: 'p-a-business-plan-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-dialog.html',
                    controller: 'PABusinessPlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PABusinessPlan', function(PABusinessPlan) {
                            return PABusinessPlan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })

        .state('p-a-business-plan.new', {
            parent: 'p-a-business-plan',
            url: '/new',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-dialog.html',
                    controller: 'PABusinessPlanDialogController',
                    controllerAs: 'vm',
                }
            },
            resolve: {
                entity: function () {
                    return {
                        businessplan: null,
                        users: null,
                        description: null,
                        pastatus: null,
                        projects: null,
                        id: null
                    };
                },
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-business-plan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })

        // .state('p-a-business-plan.new', {
        //     parent: 'p-a-business-plan',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_SUPERADMIN']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-dialog.html',
        //             controller: 'PABusinessPlanDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         businessplan: null,
        //                         users: null,
        //                         description: null,
        //                         pastatus: null,
        //                         projects: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('p-a-business-plan', null, { reload: 'p-a-business-plan' });
        //         }, function() {
        //             $state.go('p-a-business-plan');
        //         });
        //     }]
        // })
        .state('p-a-business-plan.edit', {
            parent: 'p-a-business-plan',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-dialog.html',
                    controller: 'PABusinessPlanDialogController',
                    controllerAs: 'vm',
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pABusinessPlan');
                    $translatePartialLoader.addPart('planType');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PABusinessPlan', function($stateParams, PABusinessPlan) {
                    return PABusinessPlan.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-business-plan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        // .state('p-a-business-plan.edit', {
        //     parent: 'p-a-business-plan',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_SUPERADMIN']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-dialog.html',
        //             controller: 'PABusinessPlanDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['PABusinessPlan', function(PABusinessPlan) {
        //                     return PABusinessPlan.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('p-a-business-plan', null, { reload: 'p-a-business-plan' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        .state('p-a-business-plan.delete', {
            parent: 'p-a-business-plan',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-delete-dialog.html',
                    controller: 'PABusinessPlanDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PABusinessPlan', function(PABusinessPlan) {
                            return PABusinessPlan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-business-plan', null, { reload: 'p-a-business-plan' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
