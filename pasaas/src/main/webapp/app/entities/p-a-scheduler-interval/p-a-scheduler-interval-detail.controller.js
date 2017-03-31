(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASchedulerIntervalDetailController', PASchedulerIntervalDetailController);

    PASchedulerIntervalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PASchedulerInterval', 'PAOrganization', 'PAProject'];

    function PASchedulerIntervalDetailController($scope, $rootScope, $stateParams, previousState, entity, PASchedulerInterval, PAOrganization, PAProject) {
        var vm = this;

        vm.pASchedulerInterval = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pASchedulerIntervalUpdate', function(event, result) {
            vm.pASchedulerInterval = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
