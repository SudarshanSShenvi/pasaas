(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAGeneralConfigController', PAGeneralConfigController);

    PAGeneralConfigController.$inject = ['$scope', '$state', 'PAGeneralConfig', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function PAGeneralConfigController ($scope, $state, PAGeneralConfig, ParseLinks, AlertService, paginationConstants) {
        var vm = this;

        vm.pAGeneralConfigs = [];
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

        function loadAll () {
            PAGeneralConfig.query({
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
                    vm.pAGeneralConfigs.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.pAGeneralConfigs = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
