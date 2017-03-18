(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAProjectController', PAProjectController);

    PAProjectController.$inject = ['$scope', '$state', 'PAProject', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function PAProjectController ($scope, $state, PAProject, ParseLinks, AlertService, paginationConstants) {
        $scope.page_meta_data = {
            "has_header" : true,
            "page_header_title" : "Project List",
            "breadcrumb_data" : [
                {
                    "link" : "index.html",
                    "label" : "Home",
                    "class" : "",
                    "is_active" : false
                },
                {
                    "link" : "/",
                    "label" : "Platform",
                    "class" : "",
                    "is_active" : false
                },
                {
                    "link" : "/",
                    "label" : "Project List",
                    "class" : "active",
                    "is_active" : true
                }
            ]

        };
        var vm = this;

        vm.pAProjects = [];
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
            PAProject.query({
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
                    vm.pAProjects.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.pAProjects = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
