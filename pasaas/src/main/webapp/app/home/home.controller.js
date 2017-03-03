(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'PAOrganizationUser'];

    function HomeController ($scope, Principal, LoginService, $state, PAOrganizationUser) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.paorganizations = PAOrganizationUser.queryuser();
        $scope.$on('authenticationSuccess', function() {
        	getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
