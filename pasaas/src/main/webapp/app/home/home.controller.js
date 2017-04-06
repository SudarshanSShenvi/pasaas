(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$timeout', 'ParseLinks', 'paginationConstants', 'PAPredictionScoreChartC', '$scope', 'Principal', 'LoginService', '$state', 'PAOrganization'];

    function HomeController ($timeout, ParseLinks, paginationConstants, PAPredictionScoreChartC, $scope, Principal, LoginService, $state, PAOrganization) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        // vm.previousState = previousState;

        // $scope.project_id = vm.previousState.coming_project_id;

        //vm.paorganizations = PAOrganization.query();
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
