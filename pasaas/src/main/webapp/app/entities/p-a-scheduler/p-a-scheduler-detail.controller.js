(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASchedulerDetailController', PASchedulerDetailController);

    PASchedulerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAScheduler', 'PAOrganization', 'PAProject'];

    function PASchedulerDetailController($scope, $rootScope, $stateParams, previousState, entity, PAScheduler, PAOrganization, PAProject) {
        var vm = this;

        vm.pAScheduler = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pASchedulerUpdate', function(event, result) {
            vm.pAScheduler = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
