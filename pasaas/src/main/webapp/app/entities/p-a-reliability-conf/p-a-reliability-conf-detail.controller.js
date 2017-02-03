(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReliabilityConfDetailController', PAReliabilityConfDetailController);

    PAReliabilityConfDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAReliabilityConf', 'PAOrganization', 'PAProject'];

    function PAReliabilityConfDetailController($scope, $rootScope, $stateParams, previousState, entity, PAReliabilityConf, PAOrganization, PAProject) {
        var vm = this;

        vm.pAReliabilityConf = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAReliabilityConfUpdate', function(event, result) {
            vm.pAReliabilityConf = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
