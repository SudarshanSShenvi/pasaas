(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('user-management', {
                parent: 'admin',
                url: '/user-management?page&sort',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'userManagement.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/user-management/user-management.html',
                        controller: 'UserManagementController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    }
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort)
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('user-management');
                        return $translate.refresh();
                    }]

                }
            })
            .state('user-management.su', {
                parent: 'admin',
                url: '/user-management/suops?page&sort',
                data: {
                    authorities: ['ROLE_SUPERADMIN'],
                    pageTitle: 'userManagement.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/user-management/user-management.html',
                        controller: 'UserManagementController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    }
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort)
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('user-management');
                        return $translate.refresh();
                    }]

                }
            })
            .state('user-management-detail', {
                parent: 'admin',
                url: '/user/:login/{organization}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'user-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/user-management/user-management-detail.html',
                        controller: 'UserManagementDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('user-management');
                        return $translate.refresh();
                    }],
                    previousState: ["$state", "$stateParams", function ($state, $stateParams) {
                        var orgn = $stateParams.organization;
                        var currentStateData = {
                            // name: 'p-a-organization.name({organizationName : "Vodafone"})',
                            // name: 'user-management.su',
                            // name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
                            name: $state.current.name + '({organizationName : "'+ orgn +'"})',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                    
                }
            })
            .state('user-management-detail.su', {
                parent: 'admin',
                url: '/user/:login',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'user-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/user-management/user-management-detail.html',
                        controller: 'UserManagementDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('user-management');
                        return $translate.refresh();
                    }],
                    previousState: ["$state", function($state) {
                        var currentStateData = {
                            name: $state.current.name || 'user-management.su',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            // .state('user-management.new', {
            //     parent: 'user-management',
            //     url: '/new',
            //     data: {
            //         authorities: ['ROLE_ADMIN']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
            //         $uibModal.open({
            //             templateUrl: 'app/admin/user-management/user-management-dialog.html',
            //             controller: 'UserManagementDialogController',
            //             controllerAs: 'vm',
            //             backdrop: 'static',
            //             size: 'lg',
            //             resolve: {
            //                 entity: function() {
            //                     return {
            //                         id: null,
            //                         login: null,
            //                         firstName: null,
            //                         lastName: null,
            //                         email: null,
            //                         activated: true,
            //                         langKey: null,
            //                         createdBy: null,
            //                         createdDate: null,
            //                         lastModifiedBy: null,
            //                         lastModifiedDate: null,
            //                         resetDate: null,
            //                         resetKey: null,
            //                         authorities: null
            //                     };
            //                 }
            //             }
            //         }).result.then(function() {
            //             $state.go('p-a-organization', null, { reload: true });
            //             // $state.go('user-management', null, { reload: true });
            //         }, function() {
            //             $state.go('p-a-organization');
            //             // $state.go('user-management');
            //         });
            //     }]
            // })
            .state('user-management.su.new', {
                parent: 'user-management.su',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/user-management/user-management-dialog.html',
                        controller: 'UserManagementDialogController',
                        controllerAs: 'vm',
                    }
                },
                resolve: {
                    entity: function() {
                        return {
                            id: null,
                            login: null,
                            firstName: null,
                            lastName: null,
                            email: null,
                            activated: true,
                            langKey: null,
                            createdBy: null,
                            createdDate: null,
                            lastModifiedBy: null,
                            lastModifiedDate: null,
                            resetDate: null,
                            resetKey: null,
                            authorities: null
                        };
                    },
                    previousState: ["$state", function($state) {
                        var currentStateData = {
                            name: $state.current.name || 'user-management.su',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })

            .state('user-management.new', {
                parent: 'user-management',
                url: '/{organization}/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/user-management/user-management-dialog.html',
                        controller: 'UserManagementDialogController',
                        controllerAs: 'vm',
                    }
                },
                resolve: {
                    entity: function() {
                        return {
                            id: null,
                            login: null,
                            firstName: null,
                            lastName: null,
                            email: null,
                            activated: true,
                            langKey: null,
                            createdBy: null,
                            createdDate: null,
                            lastModifiedBy: null,
                            lastModifiedDate: null,
                            resetDate: null,
                            resetKey: null,
                            authorities: null
                        };
                    },
                    previousState: ["$state", "$stateParams", function ($state, $stateParams) {
                        var orgn = $stateParams.organization;
                        var currentStateData = {
                            // name: 'p-a-organization.name({organizationName : "Vodafone"})',
                            // name: 'user-management.su',
                            // name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
                            name: $state.current.name + '({organizationName : "'+ orgn +'"})',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            // .state('user-management.su.new', {
            // 	parent: 'user-management.su',
            // 	url: '/new',
            // 	data: {
            // 		authorities: ['ROLE_ADMIN']
            // 	},
            // 	onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
            // 		$uibModal.open({
            // 			templateUrl: 'app/admin/user-management/user-management-dialog.html',
            // 			controller: 'UserManagementDialogController',
            // 			controllerAs: 'vm',
            // 			backdrop: 'static',
            // 			size: 'lg',
            // 			resolve: {
            // 				entity: function() {
            // 					return {
            // 						id: null,
            // 						login: null,
            // 						firstName: null,
            // 						lastName: null,
            // 						email: null,
            // 						activated: true,
            // 						langKey: null,
            // 						createdBy: null,
            // 						createdDate: null,
            // 						lastModifiedBy: null,
            // 						lastModifiedDate: null,
            // 						resetDate: null,
            // 						resetKey: null,
            // 						authorities: null
            // 					};
            // 				}
            // 			}
            // 		}).result.then(function() {
            // 			$state.go('user-management.su', null, { reload: 'user-management.su' });
            // 		}, function() {
            // 			$state.go('user-management.su');
            // 		});
            // 	}]
            // })
            .state('user-management.su.edit', {
                parent: 'user-management',
                url: '/{login}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/user-management/user-management-dialog.html',
                        controller: 'UserManagementDialogController',
                        controllerAs: 'vm',
                    }
                },
                resolve: {
                    entity: ['User', '$stateParams', function(User, $stateParams) {
                        return User.get({ login: $stateParams.login });
                    }],
                    previousState: ["$state", function($state) {
                        var currentStateData = {
                            name: $state.current.name || 'user-management.su',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('user-management.edit', {
                parent: 'user-management',
                url: '/{login}/{organization}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/user-management/user-management-dialog.html',
                        controller: 'UserManagementDialogController',
                        controllerAs: 'vm',
                    }
                },
                resolve: {
                    entity: ['User', '$stateParams', function(User, $stateParams) {
                        return User.get({ login: $stateParams.login });
                    }],
                    previousState: ["$state", "$stateParams", function ($state, $stateParams) {
                        var orgn = $stateParams.organization;
                        var currentStateData = {
                            prev_state_param: orgn,
                            // name: 'user-management.su',
                            // name: 'p-a-organization.name({organizationName : "Vodafone"})',
                            // name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
                            name: $state.current.name + '({organizationName : "'+ orgn +'"})',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            // .state('user-management.edit', {
            // 	parent: 'user-management',
            // 	url: '/{login}/edit',
            // 	data: {
            // 		authorities: ['ROLE_ADMIN']
            // 	},
            // 	onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
            // 		$uibModal.open({
            // 			templateUrl: 'app/admin/user-management/user-management-dialog.html',
            // 			controller: 'UserManagementDialogController',
            // 			controllerAs: 'vm',
            // 			backdrop: 'static',
            // 			size: 'lg',
            // 			resolve: {
            // 				entity: ['User', function(User) {
            // 					return User.get({ login: $stateParams.login });
            // 				}]
            // 			}
            // 		}).result.then(function() {
            // 			$state.go('user-management', null, { reload: true });
            // 		}, function() {
            // 			$state.go('^');
            // 		});
            // 	}]
            // })
            .state('user-management.delete', {
                parent: 'user-management',
                url: '/{login}/delete',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/admin/user-management/user-management-delete-dialog.html',
                        controller: 'UserManagementDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['User', function(User) {
                                return User.get({ login: $stateParams.login });
                            }]
                        }
                    }).result.then(function() {
                        $state.go('user-management', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }
})();
