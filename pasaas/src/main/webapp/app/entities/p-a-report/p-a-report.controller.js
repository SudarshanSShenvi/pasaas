(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReportController', PAReportController);

    PAReportController.$inject = ['PAReportPro', 'previousState', '$scope', '$state', 'PAReport', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function PAReportController (PAReportPro, previousState, $scope, $state, PAReport, ParseLinks, AlertService, paginationConstants) {
        var vm = this;

        vm.previousState = previousState.name;
        $scope.project_id = previousState.coming_project_id;

        vm.pAReports = [];
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
            PAReportPro.query({
                page: vm.page,
                size: vm.itemsPerPage,
                projectId: $scope.project_id,
                sort: sort()
            }, onSuccess, onError);
            // PAReport.query({
            //     page: vm.page,
            //     size: vm.itemsPerPage,
            //     sort: sort()
            // }, onSuccess, onError);
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
                    vm.pAReports.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.pAReports = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
