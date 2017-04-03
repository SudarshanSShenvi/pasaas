(function() {
	'use strict';

	angular
		.module('pasaasApp')
		.controller('NavbarController', NavbarController);

	NavbarController.$inject = ['$timeout', '$rootScope', '$scope', '$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

	function NavbarController ($timeout, $rootScope, $scope, $state, Auth, Principal, ProfileService, LoginService) {
		var vm = this;

		vm.isNavbarCollapsed = true;
		vm.isAuthenticated = Principal.isAuthenticated;

		ProfileService.getProfileInfo().then(function(response) {
			vm.inProduction = response.inProduction;
			vm.swaggerEnabled = response.swaggerEnabled;
		});

		$rootScope.$on('customEvent1', function(event) {
			console.log("Inside customEvent1");

			$timeout(function () {
		        location.reload();
		    }, 2000);
		});
		
		Principal.identity().then(function(account) {
			vm.my_acc = account;
		});

		vm.login = login;
		vm.logout = logout;
		vm.toggleNavbar = toggleNavbar;
		vm.collapseNavbar = collapseNavbar;
		vm.$state = $state;

		function login() {
			collapseNavbar();
			LoginService.open();
		}

		function logout() {
			collapseNavbar();
			Auth.logout();
			$state.go('home');
		}

		function toggleNavbar() {
			vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
		}

		function collapseNavbar() {
			vm.isNavbarCollapsed = true;
		}
	}
})();
