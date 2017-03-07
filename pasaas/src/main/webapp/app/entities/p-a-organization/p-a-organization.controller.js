(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAOrganizationController', PAOrganizationController);

    PAOrganizationController.$inject = ['$scope', '$state', '$stateParams', 'PAOrganization', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function PAOrganizationController ($scope, $state, $stateParams, PAOrganization, ParseLinks, AlertService, paginationConstants) {
        var vm = this;

        vm.pAOrganizations = [];
        vm.pAOrganizationsUser = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();
        //loadAll2();

        function loadAll () {
            PAOrganization.query({
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
        
        /*function loadAll2 () {
        	PAOrganizationUser.query({
        		user: $stateParams.user,
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
                    vm.pAOrganizationsUser.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }*/

        function reset () {
            vm.page = 0;
            vm.pAOrganizations = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
