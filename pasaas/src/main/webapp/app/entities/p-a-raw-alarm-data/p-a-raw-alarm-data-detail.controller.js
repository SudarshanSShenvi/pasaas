(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PARawAlarmDataDetailController', PARawAlarmDataDetailController);

    PARawAlarmDataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PARawAlarmData'];

    function PARawAlarmDataDetailController($scope, $rootScope, $stateParams, previousState, entity, PARawAlarmData) {
        var vm = this;

        vm.pARawAlarmData = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pARawAlarmDataUpdate', function(event, result) {
            vm.pARawAlarmData = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
