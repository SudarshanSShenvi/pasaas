(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PABusinessPlanDetailController', PABusinessPlanDetailController);

    PABusinessPlanDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PABusinessPlan', 'PAOrganization'];

    function PABusinessPlanDetailController($scope, $rootScope, $stateParams, previousState, entity, PABusinessPlan, PAOrganization) {
        var vm = this;

        vm.pABusinessPlan = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pABusinessPlanUpdate', function(event, result) {
            vm.pABusinessPlan = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
