(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAlarmActualityDetailController', PAAlarmActualityDetailController);

    PAAlarmActualityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAAlarmActuality', 'PAOrganization', 'PAProject'];

    function PAAlarmActualityDetailController($scope, $rootScope, $stateParams, previousState, entity, PAAlarmActuality, PAOrganization, PAProject) {
        var vm = this;

        vm.pAAlarmActuality = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAAlarmActualityUpdate', function(event, result) {
            vm.pAAlarmActuality = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
