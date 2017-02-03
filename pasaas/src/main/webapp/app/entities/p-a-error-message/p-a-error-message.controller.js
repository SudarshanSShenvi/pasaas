(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAErrorMessageController', PAErrorMessageController);

    PAErrorMessageController.$inject = ['$scope', '$state', 'PAErrorMessage', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function PAErrorMessageController ($scope, $state, PAErrorMessage, ParseLinks, AlertService, paginationConstants) {
        var vm = this;

        vm.pAErrorMessages = [];
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
            PAErrorMessage.query({
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
                    vm.pAErrorMessages.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.pAErrorMessages = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
