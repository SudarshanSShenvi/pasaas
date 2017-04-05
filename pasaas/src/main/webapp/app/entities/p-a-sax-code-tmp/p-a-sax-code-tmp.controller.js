(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASaxCodeTmpController', PASaxCodeTmpController);

    PASaxCodeTmpController.$inject = ['PASaxCodeTmpSu', 'PAProjectSu', '$scope', '$state', 'PASaxCodeTmp', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function PASaxCodeTmpController (PASaxCodeTmpSu, PAProjectSu, $scope, $state, PASaxCodeTmp, ParseLinks, AlertService, paginationConstants) {
        var vm = this;

        vm.project_list = PAProjectSu.query();

        vm.selected_project = {};

        vm.pASaxCodeTmps = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        vm.get_patterns = function(){
            vm.pASaxCodeTmps = [];
            loadAll();
        }


        function loadAll () {
            PASaxCodeTmpSu.query({
                page: vm.page,
                size: vm.itemsPerPage,
                projectId: vm.selected_project.id,
                sort: sort()
            }, onSuccess, onError);
            // PASaxCodeTmp.query({
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
                    vm.pASaxCodeTmps.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.pASaxCodeTmps = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
