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
				authorities: ['ROLE_ADMIN'],
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
		}).state('p-a-organization.su', {
			parent: 'entity',
			url: '/p-a-organization/suops',
			data: {
				authorities: ['ROLE_SUPERADMIN'],
				// authorities: [],
				pageTitle: 'pasaasApp.pAOrganization.home.title'
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/p-a-organization/p-a-organizations-su.html',
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
			url: '/p-a-organization/{id}/{organization}',
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
				previousState: ["$state", "$stateParams", function ($state, $stateParams) {
					var orgn = $stateParams.organization;
					var currentStateData = {
						name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
						// name: $state.current.name || 'p-a-organization',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		})
		// .state('p-a-organization.su.new', {
		.state('p-a-organization-detail.su', {
			parent: 'p-a-organization.su',
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
				previousState: ["$state", "$stateParams", function ($state, $stateParams) {
					// var orgn = $stateParams.organization;
					var currentStateData = {
						// name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
						name: $state.current.name || 'p-a-organization.su',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		})


		.state('p-a-organization.name', {
			parent: 'entity',
			url: '/p-a-organization/name/{organizationName}',
			data: {
				authorities: ['ROLE_USER'],
				// authorities: [],
				pageTitle: 'pasaasApp.pAOrganization.detail.title'
			},
			views: {
				'content@': {
					 templateUrl: 'app/entities/p-a-organization/p-a-organizations.html',
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
				entity: ['$stateParams', 'PAOrganization1', function($stateParams, PAOrganization1) {
					return PAOrganization1.get({organizationName : $stateParams.organizationName}).$promise;
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
				authorities: ['ROLE_ADMIN']
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
			url: '/{organization}/new',
			data: {
				authorities: ['ROLE_ADMIN']
				// authorities: []
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
					controller: 'PAOrganizationDialogController',
					controllerAs: 'vm',
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('pAOrganization');
					$translatePartialLoader.addPart('pAStatus');
					return $translate.refresh();
				}],
				entity: ['moment', function(moment) {
					return {
						organization: null,
						validfrom: moment().toDate(),
						validto: null,
						pastatus: 'Active',
						// pabporg: {"id":1,"businessplan":"Trial","users":1,"description":"Trial Plan","pastatus":"Active","projects":1},
						id: null
					};
				}],
				previousState: ["$state", "$stateParams", function ($state, $stateParams) {
					var orgn = $stateParams.organization;
					
					var currentStateData = {
						name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
						// name: $state.current.name || 'p-a-organization',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		})

		// .state('p-a-organization.new', {
		// 	parent: 'p-a-organization',
		// 	url: '/new',
		// 	data: {
		// 		authorities: ['ROLE_ADMIN']
		// 		// authorities: []
		// 	},
		// 	onEnter: ['moment', '$stateParams', '$state', '$uibModal', function(moment, $stateParams, $state, $uibModal) {
		// 		$uibModal.open({
		// 			templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
		// 			controller: 'PAOrganizationDialogController',
		// 			controllerAs: 'vm',
		// 			backdrop: 'static',
		// 			size: 'lg',
		// 			resolve: {
		// 				entity: function () {
		// 					return {
		// 						organization: null,
		// 						validfrom: moment().toDate(),
		// 						validto: null,
		// 						pastatus: 'Active',
		// 						// pabporg: {"id":1,"businessplan":"Trial","users":1,"description":"Trial Plan","pastatus":"Active","projects":1},
		// 						id: null
		// 					};
		// 				}
		// 			}
		// 		// }).result.then(function() {
		// 		// 	$state.go('p-a-organization.su', null, { reload: 'p-a-organization.su' });
		// 		// }, function() {
		// 		// 	$state.go('p-a-organization.su');
		// 		// });

		// 		}).result.then(function() {
		// 			// $state.go('p-a-organization', null, { reload: 'p-a-organization' });
		// 		}, function() {
		// 			// $state.go('p-a-organization');
		// 		});
		// 	}]
		// })

		.state('p-a-organization.su.new', {
			parent: 'p-a-organization.su',
			url: '/new',
			data: {
				authorities: ['ROLE_ADMIN']
				// authorities: []
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
					controller: 'PAOrganizationDialogController',
					controllerAs: 'vm',
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('pAOrganization');
					$translatePartialLoader.addPart('pAStatus');
					return $translate.refresh();
				}],
				entity: ['moment', function(moment) {
					return {
						organization: null,
						validfrom: moment().toDate(),
						validto: null,
						pastatus: 'Active',
						// pabporg: {"id":1,"businessplan":"Trial","users":1,"description":"Trial Plan","pastatus":"Active","projects":1},
						id: null
					};
				}],
				previousState: ["$state", "$stateParams", function ($state, $stateParams) {
					// var orgn = $stateParams.organization;
					
					var currentStateData = {
						// name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
						name: $state.current.name || 'p-a-organization.su',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		})
		// .state('p-a-organization.su.new', {
		// 	parent: 'p-a-organization.su',
		// 	url: '/new',
		// 	data: {
		// 		authorities: ['ROLE_ADMIN']
		// 		// authorities: []
		// 	},
		// 	onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
		// 		$uibModal.open({
		// 			templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
		// 			controller: 'PAOrganizationDialogController',
		// 			controllerAs: 'vm',
		// 			backdrop: 'static',
		// 			size: 'lg',
		// 			resolve: {
		// 				entity: function () {
		// 					return {
		// 						organization: null,
		// 						validfrom: null,
		// 						validto: null,
		// 						pastatus: null,
		// 						id: null
		// 					};
		// 				}
		// 			}
		// 		}).result.then(function() {
		// 			$state.go('p-a-organization.su', null, { reload: 'p-a-organization.su' });
		// 		}, function() {
		// 			$state.go('p-a-organization.su');
		// 		});

		// 		// }).result.then(function() {
		// 		// 	$state.go('p-a-organization', null, { reload: 'p-a-organization' });
		// 		// }, function() {
		// 		// 	$state.go('p-a-organization');
		// 		// });
		// 	}]
		// })

		.state('p-a-organization.edit', {
			parent: 'p-a-organization',
			// url: '/p-a-organization/{id}/{organization}',
			url: '/{id}/{organization}/edit',
			data: {
				authorities: ['ROLE_ADMIN']
				// authorities: []

			},
			views: {
				'content@': {
					templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
					controller: 'PAOrganizationDialogController',
					controllerAs: 'vm',
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
				previousState: ["$state", "$stateParams", function ($state, $stateParams) {
					var orgn = $stateParams.organization;
					
					var currentStateData = {
                    	prev_state_param: orgn,						
						name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
						// name: $state.current.name || 'p-a-organization',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		})

		// .state('p-a-organization.edit', {
		// 	parent: 'p-a-organization',
		// 	url: '/{id}/edit',
		// 	data: {
		// 		authorities: ['ROLE_ADMIN']
		// 		// authorities: []

		// 	},
		// 	onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
		// 		var orgn = $stateParams.organization;
		// 		$uibModal.open({
		// 			templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
		// 			controller: 'PAOrganizationDialogController',
		// 			controllerAs: 'vm',
		// 			backdrop: 'static',
		// 			size: 'lg',
		// 			resolve: {
		// 				entity: ['PAOrganization', function(PAOrganization) {
		// 					return PAOrganization.get({id : $stateParams.id}).$promise;
		// 				}]
		// 				// previousState: ["$state", "$stateParams", function ($state, $stateParams) {
		// 				// 	var orgn = $stateParams.organization;
		// 				// 	var currentStateData = {
		// 				// 		name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
		// 				// 		// name: $state.current.name || 'p-a-organization',
		// 				// 		params: $state.params,
		// 				// 		url: $state.href($state.current.name, $state.params)
		// 				// 	};
		// 				// 	return currentStateData;
		// 				// }]
		// 			}
		// 		}).result.then(function() {
		// 			// $state.go('p-a-organization.name({organizationName : "'+ orgn +'"})', null, { reload: 'p-a-organization.name({organizationName : "'+ orgn +'"})' });
		// 		}, function() {
		// 			// $state.go('p-a-organization.name({organizationName : "'+ orgn +'"})');
		// 		});
		// 		// }).result.then(function() {
		// 		// 	$state.go('p-a-organization', null, { reload: 'p-a-organization' });
		// 		// }, function() {
		// 		// 	$state.go('^');
		// 		// });
		// 	}]
		// })
		.state('p-a-organization.su.edit', {
			parent: 'p-a-organization.su',
			url: '/{id}/edit',
			data: {
				authorities: ['ROLE_ADMIN']
				// authorities: []

			},
			views: {
				'content@': {
					templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
					controller: 'PAOrganizationDialogController',
					controllerAs: 'vm',
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
				previousState: ["$state", "$stateParams", function ($state, $stateParams) {
					// var orgn = $stateParams.organization;
					
					var currentStateData = {
						// name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
						name: $state.current.name || 'p-a-organization.su',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		})
		// .state('p-a-organization.su.edit', {
		// 	parent: 'p-a-organization.su',
		// 	url: '/{id}/edit',
		// 	data: {
		// 		authorities: ['ROLE_ADMIN']
		// 		// authorities: []

		// 	},
		// 	onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
		// 		$uibModal.open({
		// 			templateUrl: 'app/entities/p-a-organization/p-a-organization-dialog.html',
		// 			controller: 'PAOrganizationDialogController',
		// 			controllerAs: 'vm',
		// 			backdrop: 'static',
		// 			size: 'lg',
		// 			resolve: {
		// 				entity: ['PAOrganization', function(PAOrganization) {
		// 					return PAOrganization.get({id : $stateParams.id}).$promise;
		// 				}]
		// 			}
		// 		}).result.then(function() {
		// 			$state.go('p-a-organization.su', null, { reload: 'p-a-organization.su' });
		// 		}, function() {
		// 			$state.go('^');
		// 		});
		// 	}]
		// })
		.state('p-a-organization.delete', {
			parent: 'p-a-organization',
			url: '/{id}/delete',
			data: {
				authorities: ['ROLE_ADMIN']
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
		})
		.state('p-a-organization.su.delete', {
			parent: 'p-a-organization.su',
			url: '/{id}/delete',
			data: {
				authorities: ['ROLE_ADMIN']
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
					$state.go('p-a-organization.su', null, { reload: 'p-a-organization.su' });
				}, function() {
					$state.go('^');
				});
			}]
		});
	}

})();
