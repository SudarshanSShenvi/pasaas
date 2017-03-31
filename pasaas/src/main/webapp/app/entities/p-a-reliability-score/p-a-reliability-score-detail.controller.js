(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReliabilityScoreDetailController', PAReliabilityScoreDetailController);

    PAReliabilityScoreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAReliabilityScore', 'PAOrganization', 'PAProject'];

    function PAReliabilityScoreDetailController($scope, $rootScope, $stateParams, previousState, entity, PAReliabilityScore, PAOrganization, PAProject) {
        var vm = this;

        vm.pAReliabilityScore = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAReliabilityScoreUpdate', function(event, result) {
            vm.pAReliabilityScore = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
