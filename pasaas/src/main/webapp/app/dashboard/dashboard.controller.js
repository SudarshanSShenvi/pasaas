(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['previousState', '$scope', '$state', 'PAOrganization'];

    function DashboardController (previousState, $scope, $state, PAOrganization) {
        var vm = this;

        vm.previousState = previousState.name;
        $scope.project_id = previousState.coming_project_id;

    }
})();
