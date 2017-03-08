(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'PAOrganizationU'];

    function HomeController ($scope, Principal, LoginService, $state, PAOrganizationU) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.pAOrganizations = null;
        $scope.$on('authenticationSuccess', function() {
        	getAccount();
        });

        getAccount();
        
        loadAll();
        
        function loadAll () {
            PAOrganizationU.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.pAOrganizations.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
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
