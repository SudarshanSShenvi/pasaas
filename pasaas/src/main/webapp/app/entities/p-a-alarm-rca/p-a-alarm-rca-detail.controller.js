(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAlarmRCADetailController', PAAlarmRCADetailController);

    PAAlarmRCADetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAAlarmRCA', 'PAProject', 'PAOrganization'];

    function PAAlarmRCADetailController($scope, $rootScope, $stateParams, previousState, entity, PAAlarmRCA, PAProject, PAOrganization) {
        var vm = this;

        vm.pAAlarmRCA = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAAlarmRCAUpdate', function(event, result) {
            vm.pAAlarmRCA = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
